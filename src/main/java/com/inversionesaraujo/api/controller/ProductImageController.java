package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Image;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.entity.ProductImage;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.ProductImageRequest;
import com.inversionesaraujo.api.service.IProduct;
import com.inversionesaraujo.api.service.IProductImage;
import com.inversionesaraujo.api.service.I_Image;

@RestController
@RequestMapping("/api/v1/productImages")
public class ProductImageController {
    @Autowired
    private IProductImage productImageService;
    @Autowired
    private IProduct productService;
    @Autowired
    private I_Image imageService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            ProductImage image = productImageService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La imagen del producto se encontro con exito")
                .data(image)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody ProductImageRequest request) {
        try {
            Product product = productService.findById(request.getProductId());
            Image image = imageService.findById(request.getImageId());

            ProductImage newImage = productImageService.save(ProductImage
                .builder()
                .image(image)
                .product(product)
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La imagen del producto se creo con exito")
                .data(newImage)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            ProductImage productImage = productImageService.findById(id);
            Image image = productImage.getImage();
            productImageService.delete(productImage);
            imageService.delete(image);
            imageService.deleteImage(image.getFirebaseId());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La imagen del producto se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
