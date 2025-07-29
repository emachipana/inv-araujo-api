package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.CategoryDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.ProductDTO;
import com.inversionesaraujo.api.business.dto.WarehouseDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.ProductRequest;
import com.inversionesaraujo.api.business.service.ICategory;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IProduct;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private IProduct productService;
    @Autowired
    private ICategory categoryService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping
    public Page<ProductDTO> getAll(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "16") Integer size,
        @RequestParam(required = false) SortBy sortby,
        @RequestParam(required = false) SortDirection direction,
        @RequestParam(required = false) String categoryName,
        @RequestParam(required = false) Integer stockLessThan,
        @RequestParam(defaultValue = "false") Boolean withDiscount,
        @RequestParam(defaultValue = "true") Boolean activeProducts
    ) {
        return productService.filterProducts(minPrice, maxPrice, categoryId, page, size, sortby, direction, categoryName, stockLessThan, withDiscount, activeProducts);
    }

    @GetMapping("{id}/warehouses")
    public List<WarehouseDTO> getWarehouses(@PathVariable Long id) {
        return productService.getWarehouses(id);
    }

    @GetMapping("{id}/related")
    public List<ProductDTO> getRelatedProducts(@PathVariable Long id) {
        ProductDTO product = productService.findById(id);

        return productService.findRelatedProducts(product.getId(), product.getCategoryId());
    }

    @GetMapping("/search")
    public Page<ProductDTO> searchProducts(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return productService.search(param, param, param, page);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        ProductDTO product = productService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El producto se encontro con exito")
            .data(product)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid ProductRequest request) {
        CategoryDTO category = categoryService.findById(request.getCategoryId());
    
        ProductDTO productToSave = productService.save(ProductDTO
            .builder()
            .description(request.getDescription())
            .name(request.getName())
            .categoryId(category.getId())
            .categoryName(category.getName())
            .brand(request.getBrand())
            .price(request.getPrice())
            .isActive(true)
            .stock(0)
            .purchasePrice(request.getPurchasePrice())
            .unit(request.getUnit())
            .isActive(request.getIsActive())
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un producto")
                .redirectTo("/productos/" + productToSave.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El producto se creo con exito")
            .data(productToSave)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid ProductRequest request, @PathVariable Long id) {
        ProductDTO product = productService.findById(id);
        product.setPrice(request.getPrice());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setIsActive(request.getIsActive());
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setUnit(request.getUnit());

        ProductDTO productUpdated = productService.save(product);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un producto")
                .redirectTo("/productos/" + productUpdated.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El producto se actualizo con exito")
            .data(productUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El producto se elimino con exito")
            .build());
    }
}
