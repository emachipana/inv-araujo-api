package com.inversionesaraujo.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.OfferProductDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.OfferProductRequest;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IOfferProduct;
import com.inversionesaraujo.api.business.service.IProduct;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/offerProducts")
public class OfferProductController {
    @Autowired
    private IOfferProduct itemService;
    @Autowired
    private IProduct productService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping("offer/{offerId}")
    public List<OfferProductDTO> getByOfferId(@PathVariable Long offerId) {
        return itemService.findByOfferId(offerId);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        OfferProductDTO item = itemService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del banner se encontro con exito")
            .data(item)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OfferProductRequest request) {
        ProductDTO product = productService.findById(request.getProductId());

        OfferProductDTO newItem = itemService.save(OfferProductDTO
            .builder()
            .product(product)
            .offerId(request.getOfferId())
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Agrego un producto al banner")
                .redirectTo("/banners/" + newItem.getOfferId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El item del banner se encontro con exito")
            .data(newItem)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id, @RequestParam(required = false) Long employeeId) {
        itemService.delete(id);

        if(employeeId != null && employeeId != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(employeeId)
                .operation("Elimino un producto del banner")
                .redirectTo("/banners/" + id)
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item del banner se elimino con exito")
            .build());
    }
}
