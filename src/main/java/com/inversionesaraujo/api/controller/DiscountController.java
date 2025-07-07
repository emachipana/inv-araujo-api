package com.inversionesaraujo.api.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.DiscountDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.DiscountRequest;
import com.inversionesaraujo.api.business.service.IDiscount;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IProduct;

import jakarta.validation.Valid;

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
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        DiscountDTO discount = discountService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El descuento se encontro con exito")
            .data(discount)
            .build());
    }
 
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid DiscountRequest request) {
        ProductDTO product = productService.findById(request.getProductId());
        Integer percentage = discountService.getPercentage(product.getPrice(), request.getPrice());

        DiscountDTO newDiscount = discountService.save(DiscountDTO
            .builder()
            .productId(product.getId())
            .price(request.getPrice())
            .percentage(percentage)
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();

            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un descuento para un producto")
                .redirectTo("/productos/" + product.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El descuento se creo con exito")
            .data(newDiscount)
            .build());
    }
    
    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid DiscountRequest request, @PathVariable Long id) {
        DiscountDTO discount = discountService.findById(id);
        ProductDTO product = productService.findById(request.getProductId());
        Integer percentage = discountService.getPercentage(product.getPrice(), request.getPrice());
        discount.setProductId(product.getId());
        discount.setPercentage(percentage);
        discount.setPrice(request.getPrice());
        DiscountDTO discountUpdated = discountService.save(discount);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();

            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo el descuento de un producto")
                .redirectTo("/productos/" + product.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El descuento se actualizo con exito")
            .data(discountUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id, @RequestBody Long employeeId) {
        DiscountDTO discount = discountService.findById(id);
        ProductDTO product = productService.findById(discount.getProductId());
        product.setDiscount(null);
        productService.save(product);
        discountService.delete(discount.getId());

        if(employeeId != null && employeeId != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(employeeId)
                .operation("Elimino el descuento de un producto")
                .redirectTo("/productos/" + product.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El descuento se elimino con exito")
            .build());
    }
}
