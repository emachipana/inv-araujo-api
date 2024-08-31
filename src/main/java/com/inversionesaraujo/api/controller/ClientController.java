package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.inversionesaraujo.api.model.entity.Client;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.service.IClient;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @Autowired
    private IClient clientService;

    @GetMapping
    public List<Client> getAll() {
        return clientService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Client client = clientService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El client se encontro con exito")
                .data(client)
                .build(), HttpStatus.OK);
        }catch(DataAccessException error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Client client) {
        try {
            client.setConsumption(0.0);
            Client clientToSave = clientService.save(client);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El client se creo con exito")
                .data(clientToSave)
                .build(), HttpStatus.CREATED);
        }catch(DataAccessException error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody Client clientBody, @PathVariable Integer id) {
        try {
            Client client = clientService.findById(id);
            clientBody.setId(id);
            clientBody.setEmail(client.getEmail());
            clientBody.setConsumption(client.getConsumption());
            Client clientToUpdate = clientService.save(clientBody);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El cliente se actualizo con exito")
                .data(clientToUpdate)
                .build(), HttpStatus.OK);
        }catch(DataAccessException error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            Client client = clientService.findById(id);
            clientService.delete(client);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El cliente se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(DataAccessException error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
