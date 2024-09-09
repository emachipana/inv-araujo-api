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

import com.inversionesaraujo.api.model.entity.Tuber;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.service.ITuber;

@RestController
@RequestMapping("/api/v1/tubers")
public class TuberController {
    @Autowired
    private ITuber tuberService;

    @GetMapping
    public List<Tuber> getAll() {
        return tuberService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Tuber tuber = tuberService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El tuberculo se encontro con exito")
                .data(tuber)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);            
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Tuber tuber) {
        try {
            Tuber newTuber = tuberService.save(tuber);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El tuberculo se creo con exito")
                .data(newTuber)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);  
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody Tuber tuberRequest) {
        try {
            Tuber tuber = tuberService.findById(id);
            tuber.setName(tuberRequest.getName());
            Tuber tuberUpdated = tuberService.save(tuber);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El tuberculo se actualizo con exito")
                .data(tuberUpdated)
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
            Tuber tuber = tuberService.findById(id);
            tuberService.delete(tuber);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El tuberculo se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND); 
        }
    }
}
