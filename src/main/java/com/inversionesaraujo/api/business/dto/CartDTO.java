package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Cart;
import com.inversionesaraujo.api.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private Double total;

    public static CartDTO toDTO(Cart cart) {
        if(cart == null) return null;

        return CartDTO
            .builder()
            .id(cart.getId())
            .total(cart.getTotal())
            .userId(cart.getUser().getId())
            .build();
    }

    public static Cart toEntity(CartDTO cart) {
        if(cart == null) return null;

        User user = new User();
        user.setId(cart.getUserId());

        return Cart
            .builder()
            .id(cart.getId())
            .user(user)
            .total(cart.getTotal())
            .build();
    }
}
