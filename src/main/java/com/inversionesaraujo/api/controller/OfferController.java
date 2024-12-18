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
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.service.IOffer;
import com.inversionesaraujo.api.model.Offer;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {
    @Autowired
    private IOffer offerService;

    @GetMapping
    public List<Offer> getAll() {
        return offerService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Offer offer = offerService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La oferta se encontro con exito")
                .data(offer)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Offer offer) {
        try {
            Offer newOffer = offerService.save(offer);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La oferta se creo con exito")
                .data(newOffer)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody Offer offer, @PathVariable Integer id) {
        try {
            Offer offerToUpdate = offerService.findById(id);
            offerToUpdate.setTitle(offer.getTitle());
            offerToUpdate.setDescription(offer.getDescription());
            offerToUpdate.setUsed(offer.isUsed());
            offerToUpdate.setMarkedWord(offer.getMarkedWord());
            Offer offerUpdated = offerService.save(offerToUpdate);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La oferta se actualizo con exito")
                .data(offerUpdated)
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
            Offer offer = offerService.findById(id);
            offerService.delete(offer);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La oferta se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
