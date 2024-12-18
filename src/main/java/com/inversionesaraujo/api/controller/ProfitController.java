package com.inversionesaraujo.api.controller;

import java.time.Month;
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

import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.dto.request.ProfitRequest;
import com.inversionesaraujo.api.business.service.IAdmin;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.model.Admin;
import com.inversionesaraujo.api.model.Profit;
import com.inversionesaraujo.api.model.SortDirection;

@RestController
@RequestMapping("/api/v1/profits")
public class ProfitController {
    @Autowired
    private IProfit profitService;
    @Autowired
    private IAdmin adminService;

    @GetMapping
    public List<Profit> getAll(@RequestParam(defaultValue = "ASC") SortDirection sort) {
        return profitService.listAll(sort);
    } 

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Profit profit = profitService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del ingreso se encontro con exito")
                .data(profit)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, ProfitRequest request) {
        try {
            Profit profit = profitService.findById(id);
            profit.setIncome(request.getIncome());
            profit.setProfit(request.getIncome() - profit.getTotalExpenses());
            Profit updatedProfit = profitService.save(profit);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del ingreso se actualizo con exito")
                .data(updatedProfit)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody ProfitRequest request) {
        try {
            Admin admin = adminService.findById(request.getAdminId());
            Month month = request.getDate().getMonth();
            Profit profit = profitService.save(Profit.builder()
                .admin(admin)
                .date(request.getDate())
                .month(month.toString())
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del ingreso se creo con exito")
                .data(profit)
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
            Profit profit = profitService.findById(id);
            profitService.delete(profit);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del ingreso se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
