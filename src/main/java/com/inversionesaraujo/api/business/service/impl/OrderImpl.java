package com.inversionesaraujo.api.business.service.impl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.dto.InvoiceItemDTO;
import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.dto.OrderProductDTO;
import com.inversionesaraujo.api.business.payload.FileResponse;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.payload.TotalDeliverResponse;
import com.inversionesaraujo.api.business.request.EmailRequest;
import com.inversionesaraujo.api.business.request.NotificationRequest;
import com.inversionesaraujo.api.business.service.IOrder;
import com.inversionesaraujo.api.business.spec.OrderSpecifications;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.NotificationType;
import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.repository.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class OrderImpl implements IOrder {
    @Autowired
    private OrderRepository orderRepo;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private NotificationImpl notiService;
    @Autowired
    private InvoiceImpl invoiceService;
    @Autowired
    private InvoiceItemImpl invoiceItemService;
    @Autowired
    private OrderProductImpl orderProductService;
    @Autowired
    private EmailImpl emailService;

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> listAll(
        Status status, Integer page, Integer size,
        SortDirection direction, Month month, SortBy sort,
        ShippingType shipType, Long warehouseId, Long employeeId,
        OrderLocation location
    ) {
        Specification<Order> spec = Specification.where(
            OrderSpecifications.findByStatus(status)
            .and(OrderSpecifications.findByMonth(month))
            .and(OrderSpecifications.findByShipType(shipType))
            .and(OrderSpecifications.findByWarehouse(warehouseId))
            .and(OrderSpecifications.findByEmployee(employeeId))
            .and(OrderSpecifications.findByLocation(location))
        );
        Pageable pageable;
        if(sort != null) {
            Sort sorted = Sort.by(Sort.Direction.fromString(direction.toString()), sort.toString());
            pageable = PageRequest.of(page, size, sorted);
        }else {
            pageable = PageRequest.of(page, size);
        }

        Page<Order> orders = orderRepo.findAll(spec, pageable);

        return OrderDTO.toPageableDTO(orders);
    }

    @Transactional
    @Override
    public OrderDTO save(OrderDTO order) {
        Order orderSaved = orderRepo.save(OrderDTO.toEntity(order, entityManager));

        return OrderDTO.toDTO(orderSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new DataAccessException("El pedido no existe") {});

        return OrderDTO.toDTO(order);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> search(String department, String city, String rsocial, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Order> orders = orderRepo
            .findByDepartmentContainingIgnoreCaseOrCityContainingIgnoreCaseOrClient_RsocialContainingIgnoreCase(
                department, city, rsocial, pageable
            );

        return OrderDTO.toPageableDTO(orders);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDataResponse getData() {
        List<Object[]> counts = orderRepo.countOrdersByStatus();
        Object[] result = counts.get(0); 

        return OrderDataResponse
            .builder()
            .ship(((Number) result[0]).longValue())
            .pen(((Number) result[1]).longValue())
            .build();
    }

    @Transactional(readOnly = true)
    @Override
    public TotalDeliverResponse totalDeliver() {
        Long total = orderRepo.totalDeliver();

        return TotalDeliverResponse
            .builder()
            .total(total)
            .build();
    }

    @Override
    public void alertNewOrder(OrderDTO order) {
        NotificationRequest notiRequest = NotificationRequest
            .builder()
            .userId(1L)
            .type(NotificationType.NEW_ORDER)
            .redirectTo("/pedidos/" + order.getId())
            .build();

        notiService.create(notiRequest);
    }

    private InvoiceDTO createInvoice(OrderDTO order) {
        LocalDateTime issueDate = LocalDateTime.now();
        InvoiceType invoiceType = order.getClient().getInvoicePreference();

        InvoiceDTO invoice = InvoiceDTO
            .builder()
            .invoiceType(order.getClient().getInvoicePreference())
            .document(order.getClient().getDocument())
            .documentType(order.getClient().getDocumentType())
            .rsocial(order.getClient().getRsocial())
            .issueDate(issueDate)
            .address(order.getClient().getCity() + ", " + order.getClient().getDepartment())
            .serie(invoiceType == InvoiceType.FACTURA ? "F001" : "B001")
            .total(0.0)
            .isSended(false)
            .build();

        InvoiceDTO savedInvoice = invoiceService.save(invoice);

        List<OrderProductDTO> orderItems = orderProductService.findByOrderId(order.getId());
        Double total = 0.0;

        for (OrderProductDTO item : orderItems) {
            Double subTotal = item.getSubTotal();

            InvoiceItemDTO invoiceItem = InvoiceItemDTO
                .builder()
                .invoiceId(savedInvoice.getId())
                .name(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .subTotal(subTotal)
                .unit(item.getProduct().getUnit().toUpperCase())
                .isIgvApply(true)
                .build();

            invoiceItemService.save(invoiceItem);
            total += subTotal;
        }

        savedInvoice.setTotal(total);
        savedInvoice = invoiceService.save(savedInvoice);

        return savedInvoice;
    }

    @Override
    public void createAndSendInvoice(OrderDTO order) {
        InvoiceDTO invoice = createInvoice(order);

        byte[] attachment = invoiceService.sendInvoiceToSunat(invoice);

        EmailRequest emailRequest = EmailRequest
            .builder()
            .destination(order.getClient().getEmail())
            .subject("Comprobante de pago - Inversiones Araujo")
            .content("Estimado cliente, adjuntamos el comprobante generado por su pedido.")
            .build();

        String filename = String.format("%s_%s.pdf", invoice.getSerie(), invoice.getRsocial().toUpperCase());

        emailService.sendEmailWithAttachment(emailRequest, attachment, filename);

        FileResponse response = invoiceService.uploadToFirebase(invoice, attachment);

        invoice.setPdfUrl(response.getFileUrl());
        invoice.setPdfFirebaseId(response.getFileName());
        invoice.setIsSended(true);

        invoiceService.save(invoice);

        System.out.println("Se completo con éxito: creación, almacenamiento y envio del comprobante");
    }
}
