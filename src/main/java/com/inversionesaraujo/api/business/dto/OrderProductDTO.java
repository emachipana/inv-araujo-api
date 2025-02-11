package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.OrderProduct;

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
public class OrderProductDTO {
    private Long id;
    private Long orderId;
    private ProductDTO product;
    private Integer quantity;
    private Double subTotal;
    private Double price;

    public static OrderProductDTO toDTO(OrderProduct item) {
        return OrderProductDTO
            .builder()
            .id(item.getId())
            .orderId(item.getOrder().getId())
            .product(ProductDTO.toDTO(item.getProduct()))
            .quantity(item.getQuantity())
            .subTotal(item.getSubTotal())
            .price(item.getPrice())
            .build();
    }

    public static OrderProduct toEntity(OrderProductDTO item) {
        Order order = new Order();
        order.setId(item.getOrderId());

        return OrderProduct
            .builder()
            .id(item.getId())
            .order(order)
            .product(ProductDTO.toEntity(item.getProduct()))
            .quantity(item.getQuantity())
            .subTotal(item.getSubTotal())
            .price(item.getPrice())
            .build();
    }
}
