package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.CartDTO;
import com.inversionesaraujo.api.business.dto.CartProductDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.CartProductRequest;
import com.inversionesaraujo.api.business.service.ICart;
import com.inversionesaraujo.api.business.service.ICartProduct;
import com.inversionesaraujo.api.business.service.IProduct;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cartProducts")
public class CartProductController {
    @Autowired
    private ICartProduct cartProductService;
    @Autowired
    private ICart cartService;
    @Autowired
    private IProduct productService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        CartProductDTO item = cartProductService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El producto del carrito se encontró con éxito")
            .data(item)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid CartProductRequest request) {
        CartDTO cart = cartService.findById(request.getCartId());
        ProductDTO product = productService.findById(request.getProductId());
        Integer quantity = request.getQuantity();

        if(quantity > product.getStock()) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("No hay suficiente stock")
                .build());
        }
        
        Double price = product.getDiscount() != null ? product.getDiscount().getPrice() : product.getPrice();
        Double subTotal = price * quantity;

        CartProductDTO newItem = cartProductService.save(CartProductDTO
            .builder()
            .cartId(cart.getId())
            .product(product)
            .price(price)
            .quantity(quantity)
            .subTotal(subTotal)
            .build());
        
        cart.setTotal(subTotal + cart.getTotal());
        cartService.save(cart);

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El producto se agregó al carrito")
            .data(newItem)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid CartProductRequest request) {
        CartProductDTO item = cartProductService.findById(id);
        Integer quantity = request.getQuantity();
        ProductDTO product = item.getProduct();

        if(quantity > product.getStock()) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("No hay suficiente stock")
                .build());
        }

        Double price = product.getDiscount() != null ? product.getDiscount().getPrice() : product.getPrice();
        Double oldSubTotal = item.getSubTotal();
        Double newSubTotal = price * quantity;

        item.setPrice(price);
        item.setQuantity(quantity);
        item.setSubTotal(newSubTotal);
        CartProductDTO itemUpdated = cartProductService.save(item);


        CartDTO cart = cartService.findById(item.getCartId());
        cart.setTotal((cart.getTotal() - oldSubTotal) + newSubTotal);
        cartService.save(cart);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El producto del carrito se modifico con éxito")
            .data(itemUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        cartProductService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El producto del carrito se eliminó con éxito")
            .build());
    }
}
