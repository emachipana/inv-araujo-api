package com.inversionesaraujo.api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.TuberDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.TuberRequest;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.ITuber;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tubers")
public class TuberController {
    @Autowired
    private ITuber tuberService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping
    public List<TuberDTO> getAll() {
        return tuberService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        TuberDTO tuber = tuberService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El tuberculo se encontro con exito")
            .data(tuber)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid TuberRequest request) {
        TuberDTO newTuber = tuberService.save(TuberDTO
            .builder()
            .name(request.getName())
            .build());

        if (request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un tipo de tuberculo")
                .redirectTo("/invitro")
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El tuberculo se creo con exito")
            .data(newTuber)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid TuberRequest request) {
        TuberDTO tuber = tuberService.findById(id);
        tuber.setName(request.getName());
        TuberDTO tuberUpdated = tuberService.save(tuber);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un tipo de tuberculo")
                .redirectTo("/invitro")
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El tuberculo se actualizo con exito")
            .data(tuberUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        tuberService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El tuberculo se elimino con exito")
            .build());
    }
}
