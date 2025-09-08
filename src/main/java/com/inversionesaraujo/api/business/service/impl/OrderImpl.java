package com.inversionesaraujo.api.business.service.impl;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import org.apache.commons.io.IOUtils;
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
import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.OrderProductDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.payload.ApiSunatResponse;
import com.inversionesaraujo.api.business.payload.AvailableHours;
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
import com.inversionesaraujo.api.model.Permission;
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
    @Autowired
    private ClientImpl clientService;
    @Autowired
    private ProfitImpl profitService;
    @Autowired
    private ProductImpl productService;

    @Transactional(readOnly = true)
    @Override
    public Page<OrderDTO> listAll(
        Status status, Integer page, Integer size,
        SortDirection direction, Month month, SortBy sort,
        ShippingType shipType, Long warehouseId, Long employeeId,
        OrderLocation location, Integer day, Long clientId
    ) {
        Specification<Order> spec = Specification.where(
            OrderSpecifications.findByStatus(status)
            .and(OrderSpecifications.findByMonth(month))
            .and(OrderSpecifications.findByShipType(shipType))
            .and(OrderSpecifications.findByWarehouse(warehouseId))
            .and(OrderSpecifications.findByEmployee(employeeId))
            .and(OrderSpecifications.findByLocation(location))
            .and(OrderSpecifications.findByDay(day))
            .and(OrderSpecifications.findByClient(clientId))
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
    public Page<OrderDTO> search(String document, String rsocial, Integer page, Status status, ShippingType shipType) {
        Pageable pageable = PageRequest.of(page, 10);
        
        // Use the first non-null and non-empty parameter as the search term
        String searchTerm = (document != null && !document.trim().isEmpty()) ? document : 
                          (rsocial != null && !rsocial.trim().isEmpty()) ? rsocial : 
                          null;
        
        Page<Order> orders = orderRepo.searchOrders(
            searchTerm,
            status,
            shipType,
            pageable
        );

        return OrderDTO.toPageableDTO(orders);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderDataResponse getData() {
        List<Object[]> counts = orderRepo.countOrdersByGroupedStatus();
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
        List<Object[]> counts = orderRepo.countPaidOrdersByShippingType();
        Object[] result = counts.get(0);

        return TotalDeliverResponse
            .builder()
            .totalAtAgency(((Number) result[0]).longValue())
            .totalAtWarehouse(((Number) result[1]).longValue())
            .build();
    }

    @Override
    public void alertNewOrder(OrderDTO order) {
        NotificationRequest notiRequest = NotificationRequest
            .builder()
            .type(NotificationType.NEW_ORDER)
            .redirectTo("/pedidos/" + order.getId())
            .build();

        notiService.sendNotificationToUsersWithPermission(notiRequest, Permission.ORDERS_WATCH, -1L);
    }

    private InvoiceDTO createInvoice(OrderDTO order) {
        LocalDateTime issueDate = LocalDateTime.now();
        InvoiceType invoiceType = order.getClient().getInvoiceDetail().getInvoicePreference();

        InvoiceDTO invoice = InvoiceDTO
            .builder()
            .invoiceType(invoiceType)
            .document(order.getClient().getInvoiceDetail().getDocument())
            .documentType(order.getClient().getInvoiceDetail().getDocumentType())
            .rsocial(order.getClient().getInvoiceDetail().getRsocial())
            .issueDate(issueDate)
            .address(order.getClient().getInvoiceDetail().getAddress())
            .serie(invoiceType == InvoiceType.FACTURA ? "F001" : "B001")
            .total(0.0)
            .isSended(false)
            .isRelatedToOrder(true)
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
                .unit(item.getProduct().getUnit())
                .isIgvApply(true)
                .build();

            invoiceItemService.save(invoiceItem);
            total += subTotal;
        }

        savedInvoice.setTotal(total);
        order.setInvoice(savedInvoice);
        savedInvoice = invoiceService.save(savedInvoice);
        orderRepo.save(OrderDTO.toEntity(order, entityManager));

        return savedInvoice;
    }

    @Override
    public Long createAndSendInvoice(OrderDTO order, Boolean sendByEmail) {
        InvoiceDTO invoice = createInvoice(order);

        ApiSunatResponse sunatResponse = invoiceService.sendInvoiceToSunat(invoice);
        
        invoice.setPdfUrl(sunatResponse.getEnlace_del_pdf());
        invoice.setIsSended(true);
        invoiceService.save(invoice);
        
        if(sendByEmail) {
            EmailRequest emailRequest = EmailRequest
            .builder()
            .destination(order.getClient().getEmail())
            .subject("Comprobante de pago - Inversiones Araujo")
            .content("Estimado cliente, adjuntamos el comprobante generado por su pedido.")
            .build();
            
            byte[] attachment = null;
            try (InputStream in = new URL(sunatResponse.getEnlace_del_pdf()).openStream()) {
                attachment = IOUtils.toByteArray(in);
            } catch (Exception e) {
                throw new RuntimeException("No se pudo descargar el PDF de SUNAT", e);
            }
            
            String filename = String.format("%s_%s.pdf", invoice.getSerie(), invoice.getRsocial().toUpperCase());

            emailService.sendEmailWithAttachment(emailRequest, attachment, filename);
        }

        System.out.println("Se completo con éxito: creación, almacenamiento y envio del comprobante");

        return invoice.getId();
    }

    @Override
    public void orderPaid(OrderDTO order) {
        ClientDTO client = order.getClient();
        Double total = order.getTotal();

        client.setConsumption(client.getConsumption() + total);
        clientService.save(client);

        Month month = order.getDate().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        if(profit == null) {
            profitService.save(ProfitDTO.builder()
                .date(order.getDate())
                .profit(total)
                .income(total)
                .month(month.toString())
                .totalExpenses(0.0)
                .build()
            );
        }else {
            Double income = profit.getIncome() + total;
            profit.setIncome(income);
            profit.setProfit(income - profit.getTotalExpenses());
            profitService.save(profit);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public AvailableHours getAvailableHours(LocalDate date) {
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(18, 0);
        int intervalMinutes = 30;

        List<LocalTime> occupied = orderRepo.findOccupiedPickupHours(date);

        List<String> available = new ArrayList<>();
        for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(intervalMinutes)) {
            if (!occupied.contains(time)) {
                available.add(time.toString());
            }
        }

        return AvailableHours.builder()
            .date(date)
            .hours(available)
            .build();
    }

    @Override
    public OrderDTO cancelOrder(OrderDTO order) {
        ClientDTO client = order.getClient();
        InvoiceDTO invoice = order.getInvoice();

        client.setConsumption(client.getConsumption() - order.getTotal());
        clientService.save(client);

        Month month = order.getDate().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        Double income = (profit.getIncome() - order.getTotal());
        profit.setIncome(income);
        profit.setProfit(income - profit.getTotalExpenses());
        profitService.save(profit);

        List<OrderProductDTO> products = orderProductService.findByOrderId(order.getId());
        
        for(OrderProductDTO item : products) {
            ProductDTO product = item.getProduct();
            Integer firstStock = product.getStock() + item.getQuantity();

            product.setStock(firstStock);
            productService.save(product);
        }

        order.setInvoice(null);
        order.setStatus(Status.CANCELADO);
        orderRepo.save(OrderDTO.toEntity(order, entityManager));

        invoiceService.delete(invoice.getId());

        return order;
    }
}
