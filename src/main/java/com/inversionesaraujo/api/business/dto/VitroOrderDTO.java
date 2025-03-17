package com.inversionesaraujo.api.business.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.VitroOrder;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitroOrderDTO {
    private Long id;
    private ClientDTO client;
    private String department;
    private String city;
    private Double total;
    private Double totalAdvance;
    private Double pending;
    private LocalDate initDate;
    private LocalDate finishDate;
    private LocalDate pickDate;
    private Status status;
    private OrderLocation location;
    private ShippingType shippingType;
    private EmployeeDTO employee;
    private Boolean isReady;
    private InvoiceDTO invoice;

    public static VitroOrderDTO toDTO(VitroOrder order) {
        return VitroOrderDTO
            .builder()
            .id(order.getId())
            .client(ClientDTO.toDTO(order.getClient()))
            .department(order.getDepartment())
            .city(order.getCity())
            .total(order.getTotal() != null ? order.getTotal() : 0)
            .totalAdvance(order.getTotalAdvance() != null ? order.getTotalAdvance() : 0)
            .pending(order.getPending() != null ? order.getPending() : 0)
            .initDate(order.getInitDate())
            .finishDate(order.getFinishDate())
            .pickDate(order.getPickDate())
            .status(order.getStatus())
            .location(order.getLocation())
            .shippingType(order.getShippingType())
            .employee(EmployeeDTO.toDTO(order.getEmployee()))
            .invoice(InvoiceDTO.toDTO(order.getInvoice()))
            .isReady(order.getIsReady())
            .build();
    }

    public static VitroOrder toEntity(VitroOrderDTO order, EntityManager entityManager) {
        return VitroOrder
            .builder()
            .id(order.getId())
            .client(ClientDTO.toEntity(order.getClient(), entityManager))
            .department(order.getDepartment())
            .city(order.getCity())
            .total(order.getTotal() != null ? order.getTotal() : 0)
            .totalAdvance(order.getTotalAdvance() != null ? order.getTotalAdvance() : 0)
            .pending(order.getPending() != null ? order.getPending() : 0)
            .initDate(order.getInitDate())
            .finishDate(order.getFinishDate())
            .pickDate(order.getPickDate())
            .status(order.getStatus())
            .location(order.getLocation())
            .shippingType(order.getShippingType())
            .employee(EmployeeDTO.toEntity(order.getEmployee(), entityManager))
            .invoice(InvoiceDTO.toEntity(order.getInvoice()))
            .isReady(order.getIsReady() != null ? order.getIsReady() : false)
            .build();
    }

    public static Page<VitroOrderDTO> toPageableDTO(Page<VitroOrder> orders) {
        return orders.map(VitroOrderDTO::toDTO);
    }

    public static List<VitroOrderDTO> toListDTO(List<VitroOrder> orders) {
        return orders
            .stream()
            .map(VitroOrderDTO::toDTO)
            .collect(Collectors.toList());
    }
}
