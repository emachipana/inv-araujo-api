package com.inversionesaraujo.api.controller;

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
import com.inversionesaraujo.api.business.dto.request.VarietyRequest;
import com.inversionesaraujo.api.business.service.ITuber;
import com.inversionesaraujo.api.business.service.IVariety;
import com.inversionesaraujo.api.model.Tuber;
import com.inversionesaraujo.api.model.Variety;

@RestController
@RequestMapping("/api/v1/varieties")
public class VarietyController {
    @Autowired
    private IVariety varietyService;
    @Autowired
    private ITuber tuberService;
    
    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Variety variety = varietyService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La variedad se encontro con exito")
                .data(variety)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody VarietyRequest request) {
        try {
            Tuber tuber = tuberService.findById(request.getTuberId());
            Variety variety = varietyService.save(Variety
                .builder()
                .tuber(tuber)
                .price(request.getPrice())
                .name(request.getName())
                .minPrice(request.getMinPrice())
                .build());
            
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La variedad se creo con exito")
                .data(variety)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody VarietyRequest request) {
        try {
            Variety variety = varietyService.findById(id);
            variety.setName(request.getName());
            variety.setPrice(request.getPrice());
            variety.setMinPrice(request.getMinPrice());
            Variety varietyUpdated = varietyService.save(variety);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La variedad se actualizo con exito")
                .data(varietyUpdated)
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
            Variety variety = varietyService.findById(id);
            varietyService.delete(variety);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("La variedad se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
