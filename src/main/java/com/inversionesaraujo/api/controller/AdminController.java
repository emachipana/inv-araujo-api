package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Admin;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.service.IAdmin;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {
    @Autowired
    private IAdmin adminService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Admin admin = adminService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El administrador se encontro con exito")
                .data(admin)
                .build(), HttpStatus.OK);
        }catch (Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody Admin adminBody, @PathVariable Integer id) {
        try {
            Admin admin = adminService.findById(id);
            adminBody.setId(id);
            adminBody.setTotalProfit(admin.getTotalProfit());
            adminBody.setEmail(admin.getEmail());
            Admin adminToUpdate = adminService.save(adminBody);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El administrador fue actualizado con exito")
                .data(adminToUpdate)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
