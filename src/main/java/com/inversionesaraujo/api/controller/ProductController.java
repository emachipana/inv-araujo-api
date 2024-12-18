package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import com.inversionesaraujo.api.model.entity.Category;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.ProductRequest;
import com.inversionesaraujo.api.service.ICategory;
import com.inversionesaraujo.api.service.IProduct;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private IProduct productService;
    @Autowired
    private ICategory categoryService;

    @GetMapping
    public Page<Product> getAll(
        @RequestParam(required = false) Integer categoryId,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(required = false) SortDirection sort
    ) {
        return productService.filterProducts(minPrice, maxPrice, categoryId, page, size, sort);
    }

    @GetMapping("/search")
    public Page<Product> searchProducts(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return productService.search(param, param, param, page);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Product product = productService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El producto se encontro con exito")
                .data(product)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody ProductRequest productRequest) {
        try {
            Category category = categoryService.findById(productRequest.getCategoryId());
            Product productToSave = productService.save(Product
                .builder()
                .category(category)
                .description(productRequest.getDescription())
                .isActive(productRequest.getIsActive())
                .name(productRequest.getName())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .purchasePrice(productRequest.getPurchasePrice())
                .stock(productRequest.getStock())
                .unit(productRequest.getUnit())
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El producto se creo con exito")
                .data(productToSave)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody ProductRequest productRequest, @PathVariable Integer id) {
        try {
            Category category = categoryService.findById(productRequest.getCategoryId());
            Product product = productService.findById(id);
            product.setCategory(category);
            product.setPrice(productRequest.getPrice());
            product.setPurchasePrice(productRequest.getPurchasePrice());
            product.setActive(productRequest.getIsActive());
            product.setStock(productRequest.getStock());
            product.setName(productRequest.getName());
            product.setBrand(productRequest.getBrand());
            product.setDescription(productRequest.getDescription());
            product.setUnit(productRequest.getUnit());

            Product productUpdated = productService.save(product);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El producto se actualizo con exito")
                .data(productUpdated)
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
            Product product = productService.findById(id);
            productService.delete(product);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El producto se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
