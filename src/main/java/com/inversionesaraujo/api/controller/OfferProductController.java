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

import com.inversionesaraujo.api.model.entity.Offer;
import com.inversionesaraujo.api.model.entity.OfferProduct;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.OfferProductRequest;
import com.inversionesaraujo.api.service.IOffer;
import com.inversionesaraujo.api.service.IOfferProduct;
import com.inversionesaraujo.api.service.IProduct;

@RestController
@RequestMapping("/api/v1/offerProducts")
public class OfferProductController {
    @Autowired
    private IOfferProduct itemService;
    @Autowired
    private IOffer offerService;
    @Autowired
    private IProduct productService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            OfferProduct item = itemService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item de la oferta se encontro con exito")
                .data(item)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody OfferProductRequest request) {
        try {
            Offer offer = offerService.findById(request.getOfferId());
            Product product = productService.findById(request.getProductId());
            OfferProduct newItem = itemService.save(OfferProduct
                .builder()
                .product(product)
                .offer(offer)
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item de la oferta se encontro con exito")
                .data(newItem)
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
            OfferProduct item = itemService.findById(id);
            itemService.delete(item);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item de la oferta se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
