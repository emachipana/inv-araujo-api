package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Cart;
import com.inversionesaraujo.api.model.CartProduct;

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
public class CartProductDTO {
    private Long id;
    private ProductDTO product;
    private Long cartId;
    private Integer quantity;
    private Double price;
    private Double subTotal;

    public static CartProductDTO toDTO(CartProduct cartProduct) {
        return CartProductDTO
            .builder()
            .id(cartProduct.getId())
            .product(ProductDTO.toDTO(cartProduct.getProduct()))
            .cartId(cartProduct.getCart().getId())
            .quantity(cartProduct.getQuantity())
            .price(cartProduct.getPrice())
            .subTotal(cartProduct.getSubTotal())
            .build();
    }

    public static CartProduct toEntity(CartProductDTO cartProduct) {
        Cart cart = new Cart();
        cart.setId(cartProduct.getCartId());
        
        return CartProduct
            .builder()
            .id(cartProduct.getId())
            .product(ProductDTO.toEntity(cartProduct.getProduct()))
            .cart(cart)
            .quantity(cartProduct.getQuantity())
            .price(cartProduct.getPrice())
            .subTotal(cartProduct.getSubTotal())
            .build();
    }
}
