package com.inversionesaraujo.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.RemoveStockDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.RemoveStockRequest;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IRemoveStock;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/removeStock")
public class RemoveStockController {
    @Autowired
    private IRemoveStock removeStockService;
    @Autowired
    private IEmployee employeeService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @PostMapping
    public ResponseEntity<MessageResponse> removeStock(@RequestBody @Valid RemoveStockRequest request) {
        EmployeeDTO employee = employeeService.findById(request.getEmployeeId());

        RemoveStockDTO removeStock = removeStockService.create(RemoveStockDTO
            .builder()
            .productId(request.getProductId())
            .quantity(request.getQuantity())
            .reason(request.getReason())
            .employee(employee)
            .build()
        );

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Descontó stock de un producto")
                .redirectTo("/productos/" + request.getProductId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El stock se desconto con exito")
            .data(removeStock)
            .build());
    }

    @GetMapping("/product/{productId}")
    public List<RemoveStockDTO> getRemoveStockByProductId(@PathVariable Long productId) {
        return removeStockService.findByProductId(productId);
    }
}
