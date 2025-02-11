package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ImageDTO;
import com.inversionesaraujo.api.business.dto.ProductImageDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.ProductImageRequest;
import com.inversionesaraujo.api.business.service.IProductImage;
import com.inversionesaraujo.api.business.service.I_Image;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productImages")
public class ProductImageController {
    @Autowired
    private IProductImage productImageService;
    @Autowired
    private I_Image imageService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        ProductImageDTO image = productImageService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La imagen del producto se encontro con exito")
            .data(image)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid ProductImageRequest request) {
        ImageDTO image = imageService.findById(request.getImageId());

        ProductImageDTO newImage = productImageService.save(ProductImageDTO
            .builder()
            .image(image)
            .productId(request.getProductId())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("La imagen del producto se creo con exito")
            .data(newImage)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        ProductImageDTO productImage = productImageService.findById(id);
        ImageDTO image = productImage.getImage();
        productImageService.delete(id);
        imageService.delete(image.getId());
        imageService.deleteImage(image.getFirebaseId());

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La imagen del producto se elimino con exito")
            .build());
    }
}
