package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Discount;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.DiscountRequest;
import com.inversionesaraujo.api.service.IDiscount;
import com.inversionesaraujo.api.service.IProduct;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {
    @Autowired
    private IDiscount discountService;
    @Autowired
    private IProduct productService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Discount discount = discountService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El descuento se encontro con exito")
                .data(discount)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.OK);
        }
    }
 
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody DiscountRequest discountRequest) {
        try {
            Product product = productService.findById(discountRequest.getProductId());
            Integer percentage = discountService.getPercentage(product.getPrice(), discountRequest.getPrice());

            Discount newDiscount = discountService.save(Discount
                .builder()
                .product(product)
                .price(discountRequest.getPrice())
                .percentage(percentage)
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El descuento se creo con exito")
                .data(newDiscount)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody DiscountRequest discountRequest, @PathVariable Integer id) {
        try {
            Discount discountToUpdate = discountService.findById(id);
            Product product = productService.findById(discountRequest.getProductId());
            Integer percentage = discountService.getPercentage(product.getPrice(), discountRequest.getPrice());
            discountToUpdate.setProduct(product);
            discountToUpdate.setPercentage(percentage);
            discountToUpdate.setPrice(discountRequest.getPrice());
            Discount discountUpdated = discountService.save(discountToUpdate);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El descuento se actualizo con exito")
                .data(discountUpdated)
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
            Discount discount = discountService.findById(id);
            discountService.delete(discount);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El descuento se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
