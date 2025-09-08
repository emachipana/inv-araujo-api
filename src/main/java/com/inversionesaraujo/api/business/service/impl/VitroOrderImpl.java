package com.inversionesaraujo.api.business.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
import com.inversionesaraujo.api.business.dto.MonthlyProductionDTO;
import com.inversionesaraujo.api.business.dto.OrderVarietyDTO;
import com.inversionesaraujo.api.business.dto.VarietyQuantityDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.ApiSunatResponse;
import com.inversionesaraujo.api.business.payload.AvailbleByMonth;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.payload.TotalDeliverResponse;
import com.inversionesaraujo.api.business.request.NotificationRequest;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.NotificationType;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.ProductUnit;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.VitroOrder;
import com.inversionesaraujo.api.repository.VitroOrderRepository;
import com.inversionesaraujo.api.business.spec.VitroOrderSpecifications;

@Service
public class VitroOrderImpl implements IVitroOrder {
    @Autowired
    private VitroOrderRepository orderRepo;
    @Autowired
    private InvoiceImpl invoiceService;
    @Autowired
    private OrderVarietyImpl varietyService;
    @Autowired
    private InvoiceItemImpl invoiceItemService;
    @Autowired
    private NotificationImpl notiService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Page<VitroOrderDTO> listAll(
        Long tuberId, Integer page, Integer size, 
        SortDirection direction, Month month, Status status,
        SortBy sortby, ShippingType shipType, Boolean ordersReady,
        Long employeeId, OrderLocation location, Integer day, Long clientId, Double pending
    ) {
        Specification<VitroOrder> spec = Specification.where(
            VitroOrderSpecifications.findByTuberId(tuberId)
            .and(VitroOrderSpecifications.findByMonth(month))
            .and(VitroOrderSpecifications.findByStatus(status))
            .and(VitroOrderSpecifications.findByShipType(shipType))
            .and(VitroOrderSpecifications.ordersReady(ordersReady))
            .and(VitroOrderSpecifications.findByEmployee(employeeId))
            .and(VitroOrderSpecifications.findByLocation(location))
            .and(VitroOrderSpecifications.findByDay(day))
            .and(VitroOrderSpecifications.findByClient(clientId))
            .and(VitroOrderSpecifications.filterByPending(pending))
        );
        Pageable pageable;
        if(sortby != null) {
            Sort sorted = Sort.by(Sort.Direction.fromString(direction.toString()), sortby.toString());
            pageable = PageRequest.of(page, size, sorted);
        }else {
            pageable = PageRequest.of(page, size);
        }

        Page<VitroOrder> orders = orderRepo.findAll(spec, pageable);

        return VitroOrderDTO.toPageableDTO(orders);
    }

    @Transactional
    @Override
    public VitroOrderDTO save(VitroOrderDTO vitroOrder) {
        VitroOrder vitroOrderSaved = orderRepo.save(VitroOrderDTO.toEntity(vitroOrder, entityManager)); 

        return VitroOrderDTO.toDTO(vitroOrderSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public VitroOrderDTO findById(Long id) {
        VitroOrder order = orderRepo.findById(id).orElseThrow(() -> new DataAccessException("El pedido invitro no existe") {});

        return VitroOrderDTO.toDTO(order);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VitroOrderDTO> search(String document, String rsocial, Double pending, Boolean isReady, ShippingType shipType, Status status, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        
        // Prepare search term - if both are null/empty, pass empty string to get all records
        String searchTerm = null;
        if (document != null && !document.trim().isEmpty()) {
            searchTerm = document.trim();
        } else if (rsocial != null && !rsocial.trim().isEmpty()) {
            searchTerm = rsocial.trim();
        } else {
            searchTerm = "";
        }
        
        // Log the search parameters for debugging
        System.out.println("Searching with params - searchTerm: " + searchTerm + 
                         ", pending: " + pending + 
                         ", isReady: " + isReady + 
                         ", shipType: " + shipType + 
                         ", status: " + status);
        
        Page<VitroOrder> orders = orderRepo.searchOrders(
            searchTerm,
            pending,
            isReady,
            shipType,
            status,
            pageable
        );

        System.out.println("Found " + orders.getTotalElements() + " orders");

        return VitroOrderDTO.toPageableDTO(orders);
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

    @Transactional(readOnly = true)
    @Override
    public List<MonthlyProductionDTO> getMonthlyProductionSummary() {
        List<MonthlyProductionDTO> result = new ArrayList<>();
        
        List<Object[]> months = orderRepo.findMonthsWithOrders();
        
        for (Object[] monthData : months) {
            Integer year = (Integer) monthData[0];
            Integer month = (Integer) monthData[1];
            
            List<Object[]> varietyQuantities = orderRepo.findVarietyQuantitiesByMonth(year, month);
            
            List<VarietyQuantityDTO> varietyDTOs = varietyQuantities.stream()
                .map(arr -> new VarietyQuantityDTO(
                    (String) arr[0],
                    (String) arr[1],
                    ((Number) arr[2]).intValue()
                ))
                .collect(Collectors.toList());
            
            result.add(new MonthlyProductionDTO(year, month, varietyDTOs));
        }
        
        return result;
    }
    
    @Override
    public AvailbleByMonth availableByMonth(LocalDate date, Integer requestedQuantity) {
        final int monthLimit = 8000;
        final int minProduction = 500;
    
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        YearMonth targetMonth = YearMonth.from(date);
    
        YearMonth firstAvailableMonth = currentMonth.plusMonths(1);
        if (targetMonth.isBefore(firstAvailableMonth)) {
            return createResponse(requestedQuantity, 0,
                "La producción debe solicitarse con al menos un mes de anticipación.");
        }
    
        int remaining = requestedQuantity;
        YearMonth month = firstAvailableMonth;
    
        while (!month.isAfter(targetMonth) && remaining > 0) {
            int producedThisMonth = orderRepo.sumCantidadByMonth(month.getYear(), month.getMonthValue());

            int availableThisMonth = Math.max(0, monthLimit - producedThisMonth);
    
            if (availableThisMonth >= minProduction) {
                int assign = Math.min(availableThisMonth, remaining);
    
                if (assign < minProduction && assign < remaining) {
                    assign = 0;
                }
    
                remaining -= assign;
            }
    
            month = month.plusMonths(1);
        }
    
        if (remaining <= 0) {
            return createResponse(requestedQuantity, requestedQuantity,
                "Producción disponible para la fecha solicitada.");
        } else {
            int produced = requestedQuantity - remaining;

            if(produced < minProduction) {
                return createResponse(requestedQuantity, 0,
                    "No contamos con la capacidad para cumplir con el pedido.");
            }

            return createResponse(requestedQuantity, produced,
                String.format("Solo podemos producir %d de las %d plantas solicitadas hasta la fecha indicada.",
                    produced, requestedQuantity));
        }
    }
    
    private AvailbleByMonth createResponse(int requestedQuantity, int availableQuantity, String message) {
        AvailbleByMonth response = new AvailbleByMonth();
        response.setIsAvailable(availableQuantity >= requestedQuantity);
        response.setMessage(message);
        return response;
    }

    @Override
    public Long createAndSendInvoice(VitroOrderDTO order) {
        InvoiceDTO invoice = createInvoice(order);

        ApiSunatResponse sunatResponse = invoiceService.sendInvoiceToSunat(invoice);

        invoice.setPdfUrl(sunatResponse.getEnlace_del_pdf());
        invoice.setIsSended(true);

        invoiceService.save(invoice);

        return invoice.getId();
    }

    private InvoiceDTO createInvoice(VitroOrderDTO order) {
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

        List<OrderVarietyDTO> varieties = varietyService.findByVitroOrderId(order.getId());
        Double total = 0.0;

        for (OrderVarietyDTO variety : varieties) {
            Double subTotal = variety.getSubTotal();

            InvoiceItemDTO invoiceItem = InvoiceItemDTO
                .builder()
                .invoiceId(savedInvoice.getId())
                .name(variety.getVariety().getTuberName() + " (" + variety.getVariety().getName() + ")")
                .quantity(variety.getQuantity())
                .price(variety.getPrice())
                .subTotal(subTotal)
                .unit(ProductUnit.NIU)
                .isIgvApply(true)
                .build();

            invoiceItemService.save(invoiceItem);
            total += subTotal;
        }

        savedInvoice.setTotal(total);
        order.setInvoice(savedInvoice);
        savedInvoice = invoiceService.save(savedInvoice);
        orderRepo.save(VitroOrderDTO.toEntity(order, entityManager));

        return savedInvoice;
    }

    @Override
    public void alertNewOrder(VitroOrderDTO order) {
        NotificationRequest request = NotificationRequest
            .builder()
            .type(NotificationType.NEW_VITRO_ORDER)
            .userId(1L)
            .redirectTo("/invitro/" + order.getId())
            .build();

        notiService.create(request);
    }
}
