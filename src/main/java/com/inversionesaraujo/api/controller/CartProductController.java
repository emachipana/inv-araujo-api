package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.dto.request.CartProductRequest;
import com.inversionesaraujo.api.business.service.ICart;
import com.inversionesaraujo.api.business.service.ICartProduct;
import com.inversionesaraujo.api.business.service.IProduct;
import com.inversionesaraujo.api.model.Cart;
import com.inversionesaraujo.api.model.CartProduct;
import com.inversionesaraujo.api.model.Product;

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
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            CartProduct item = cartProductService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El producto del carrito se encontró con éxito")
                .data(item)
                .build(), HttpStatus.OK);
        } catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody CartProductRequest request) {
        try {
            Cart cart = cartService.findById(request.getCartId());
            Product product = productService.findById(request.getProductId());
            Integer quantity = request.getQuantity();

            if(quantity > product.getStock()) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("No hay suficiente stock")
                    .build(), HttpStatus.NOT_ACCEPTABLE);
            }
            
            Double price = product.getDiscount() != null ? product.getDiscount().getPrice() : product.getPrice();
            Double subTotal = price * quantity;

            CartProduct newItem = cartProductService.save(CartProduct
                .builder()
                .cart(cart)
                .product(product)
                .price(price)
                .quantity(quantity)
                .subTotal(subTotal)
                .build());
            
            cart.setTotal(subTotal + cart.getTotal());
            cartService.save(cart);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El producto se agregó al carrito")
                .data(newItem)
                .build(), HttpStatus.CREATED);
        } catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody CartProductRequest request) {
        try {
            CartProduct item = cartProductService.findById(id);
            Integer quantity = request.getQuantity();
            Product product = item.getProduct();

            if(quantity > product.getStock()) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("No hay suficiente stock")
                    .build(), HttpStatus.NOT_ACCEPTABLE);
            }

            Double price = product.getDiscount() != null ? product.getDiscount().getPrice() : product.getPrice();
            Double oldSubTotal = item.getSubTotal();
            Double newSubTotal = price * quantity;

            item.setPrice(price);
            item.setQuantity(quantity);
            item.setSubTotal(newSubTotal);
            CartProduct itemUpdated = cartProductService.save(item);

            Cart cart = item.getCart();
            cart.setTotal((cart.getTotal() - oldSubTotal) + newSubTotal);
            cartService.save(cart);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El producto del carrito se modifico con éxito")
                .data(itemUpdated)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {

        }catch()
    }
}
