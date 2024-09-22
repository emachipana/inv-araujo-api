package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
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
    public List<Product> getAll(
        @RequestParam(required = false) Integer categoryId,
        @RequestParam(required = false) Double priceMin,
        @RequestParam(required = false) Double priceMax
    ) {
        if(categoryId != null && priceMax != null && priceMin != null) {
            Category category = categoryService.findById(categoryId);
            return productService.findByCategoryAndPrice(category, priceMin, priceMax);
        }else if(priceMax != null && priceMin != null) {
            return productService.findByPrice(priceMin, priceMax);
        }else if(categoryId != null && priceMax != null) {
            Category category = categoryService.findById(categoryId);
            return productService.findByCategoryAndPriceLessThan(category, priceMax);
        }else if(categoryId != null && priceMin != null) {
            Category category = categoryService.findById(categoryId);
            return productService.findByCategoryAndPriceGreaterThan(category, priceMin);
        }else if(categoryId != null) {
            Category category = categoryService.findById(categoryId);
            List<Product> products = productService.findByCategory(category);
            return products.isEmpty() ? productService.findBySubCategories(category) : products;
        }else if(priceMax != null) {
            return productService.findByPriceLessThan(priceMax);
        }else if(priceMin != null) {
            return productService.findByPriceGreaterThan(priceMin);
        }else {
            return productService.listAll();
        }
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String param) {
        return productService.search(param, param);
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
    public ResponseEntity<MessageResponse> create(@RequestBody ProductRequest product) {
        try {
            Category category = categoryService.findById(product.getCategoryId());
            Product productToSave = productService.save(Product
                .builder()
                .category(category)
                .description(product.getDescription())
                .isActive(product.getIsActive())
                .name(product.getName())
                .brand(product.getBrand())
                .price(product.getPrice())
                .stock(product.getStock())
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
            product.setActive(productRequest.getIsActive());
            product.setStock(productRequest.getStock());
            product.setName(productRequest.getName());
            product.setBrand(productRequest.getBrand());
            product.setDescription(productRequest.getDescription());

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
