package com.inversionesaraujo.api.business.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.PaymentType;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private ClientDTO client;
    private Status status;
    private OrderLocation location;
    private Double total;
    private InvoiceDTO invoice;
    private String department;
    private String city;
    private LocalDate date;
    private LocalDate maxShipDate;
    private ShippingType shippingType;
    private EmployeeDTO employee;
    private WarehouseDTO warehouse;
    private ImageDTO evidence;
    private PaymentType paymentType;
    private ReceiverInfoDTO receiverInfo;
    private PickupInfoDTO pickupInfo;

    public static OrderDTO toDTO(Order order) {
        return OrderDTO
            .builder()
            .id(order.getId())
            .client(ClientDTO.toDTO(order.getClient()))
            .status(order.getStatus())
            .location(order.getLocation())
            .total(order.getTotal() == null ? 0.0 : order.getTotal())
            .invoice(InvoiceDTO.toDTO(order.getInvoice()))
            .department(order.getDepartment())
            .city(order.getCity())
            .date(order.getDate())
            .maxShipDate(order.getMaxShipDate())
            .shippingType(order.getShippingType())
            .employee(EmployeeDTO.toDTO(order.getEmployee()))
            .warehouse(WarehouseDTO.toDTO(order.getWarehouse(), 0))
            .evidence(ImageDTO.toDTO(order.getImage()))
            .paymentType(order.getPaymentType())
            .receiverInfo(ReceiverInfoDTO.toDTO(order.getReceiverInfo()))
            .pickupInfo(PickupInfoDTO.toDTO(order.getPickupInfo()))
            .build();
    }

    public static Order toEntity(OrderDTO order, EntityManager entityManager) {
        return Order
            .builder()
            .id(order.getId())
            .client(ClientDTO.toEntity(order.getClient(), entityManager))
            .status(order.getStatus())
            .location(order.getLocation())
            .total(order.getTotal())
            .invoice(InvoiceDTO.toEntity(order.getInvoice()))
            .department(order.getDepartment())
            .city(order.getCity())
            .date(order.getDate())
            .maxShipDate(order.getMaxShipDate())
            .warehouse(WarehouseDTO.toEntity(order.getWarehouse()))
            .shippingType(order.getShippingType())
            .employee(EmployeeDTO.toEntity(order.getEmployee(), entityManager))
            .image(ImageDTO.toEntity(order.getEvidence()))
            .paymentType(order.getPaymentType())
            .receiverInfo(ReceiverInfoDTO.toEntity(order.getReceiverInfo()))
            .pickupInfo(PickupInfoDTO.toEntity(order.getPickupInfo()))
            .build();
    }

    public static Page<OrderDTO> toPageableDTO(Page<Order> orders) {
        return orders.map(OrderDTO::toDTO);
    }

    public static List<OrderDTO> toListDTO(List<Order> orders) {
        return orders
            .stream()
            .map(OrderDTO::toDTO)
            .collect(Collectors.toList());
    }
}
