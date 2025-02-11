package com.inversionesaraujo.api.controller;

import java.time.Month;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.ProfitRequest;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.model.SortDirection;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/profits")
public class ProfitController {
    @Autowired
    private IProfit profitService;

    @GetMapping
    public List<ProfitDTO> getAll(@RequestParam(defaultValue = "ASC") SortDirection sort) {
        return profitService.listAll(sort);
    } 

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        ProfitDTO profit = profitService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El registro del ingreso se encontro con exito")
            .data(profit)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid ProfitRequest request) {
        ProfitDTO profit = profitService.findById(id);
        profit.setIncome(request.getIncome());
        profit.setProfit(request.getIncome() - profit.getTotalExpenses());
        ProfitDTO updatedProfit = profitService.save(profit);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El registro del ingreso se actualizo con exito")
            .data(updatedProfit)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid ProfitRequest request) {
        Month month = request.getDate() == null ? LocalDate.now().getMonth() : request.getDate().getMonth();
        ProfitDTO profit = profitService.save(ProfitDTO
            .builder()
            .date(request.getDate())
            .month(month.toString())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El registro del ingreso se creo con exito")
            .data(profit)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        profitService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El registro del ingreso se elimino con exito")
            .build());
    }
}
