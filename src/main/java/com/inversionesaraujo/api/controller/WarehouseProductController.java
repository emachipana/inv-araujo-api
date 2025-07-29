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

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.WarehouseProductDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.WarehouseProductRequest;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IProduct;
import com.inversionesaraujo.api.business.service.IWarehouseProduct;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/warehouseProducts")
public class WarehouseProductController {
    @Autowired
    private IWarehouseProduct itemService;
    @Autowired
    private IProduct productService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        WarehouseProductDTO item = itemService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item se encontró con éxito")
            .data(item)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid WarehouseProductRequest request) {
        ProductDTO product = productService.findById(request.getProductId());
        WarehouseProductDTO existingItem = itemService.existingItem(product.getId(), request.getWarehouseId());

        WarehouseProductDTO itemSaved;
        if(existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            itemSaved = itemService.save(existingItem);
        }else {
            itemSaved = itemService.save(WarehouseProductDTO
                .builder()
                .quantity(request.getQuantity())
                .productId(product.getId())
                .warehouseId(request.getWarehouseId())
                .build());
        }

        product.setStock(product.getStock() + request.getQuantity());
        productService.save(product);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Registro un nuevo lote")
                .redirectTo("/productos")
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El item se creó con éxito")
            .data(itemSaved)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid WarehouseProductRequest request, @PathVariable Long id) {
        WarehouseProductDTO item = itemService.findById(id);
        ProductDTO product = productService.findById(item.getProductId());
        Integer oldQuantity = item.getQuantity();

        item.setQuantity(request.getQuantity());
        WarehouseProductDTO itemUpdated = itemService.save(item);

        product.setStock((product.getStock() - oldQuantity) + request.getQuantity());
        productService.save(product);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item se actualizó con éxito")
            .data(itemUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        WarehouseProductDTO item = itemService.findById(id);
        Integer quantity = item.getQuantity();
        ProductDTO product = productService.findById(item.getProductId());

        itemService.delete(id);
        
        product.setStock(product.getStock() - quantity);
        productService.save(product);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item se eliminó con éxito")
            .build());
    }
}
