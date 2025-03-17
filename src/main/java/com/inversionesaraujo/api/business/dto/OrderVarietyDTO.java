package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.OrderVariety;
import com.inversionesaraujo.api.model.VitroOrder;

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
public class OrderVarietyDTO {
    private Long id;
    private Long vitroOrderId;
    private VarietyDTO variety;
    private Double price;
    private Integer quantity;
    private Double subTotal;

    public static OrderVarietyDTO toDTO(OrderVariety item) {
        return OrderVarietyDTO
            .builder()
            .id(item.getId())
            .vitroOrderId(item.getVitroOrder().getId())
            .variety(VarietyDTO.toDTO(item.getVariety()))
            .price(item.getPrice())
            .quantity(item.getQuantity())
            .subTotal(item.getSubTotal())
            .build();
    }

    public static OrderVariety toEntity(OrderVarietyDTO item) {
        VitroOrder order = new VitroOrder();
        order.setId(item.getVitroOrderId());

        return OrderVariety
            .builder()
            .id(item.getId())
            .vitroOrder(order)
            .variety(VarietyDTO.toEntity(item.getVariety()))
            .price(item.getPrice())
            .quantity(item.getQuantity())
            .subTotal(item.getSubTotal())
            .build();
    }

    public static List<OrderVarietyDTO> toDTOList(List<OrderVariety> items) {
        return items
            .stream()
            .map(OrderVarietyDTO::toDTO)
            .collect(Collectors.toList());
    }
}
