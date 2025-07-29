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
import com.inversionesaraujo.api.business.dto.OfferDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.OfferRequest;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IOffer;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {
    @Autowired
    private IOffer offerService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping
    public List<OfferDTO> getAll() {
        return offerService.listAll();
    }

    @GetMapping("/used")
    public List<OfferDTO> getAllUsed() {
        return offerService.listUsedBanners();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        OfferDTO offer = offerService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El banner se encontro con exito")
            .data(offer)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OfferRequest request) {
        OfferDTO newOffer = offerService.save(OfferDTO
            .builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .markedWord(request.getMarkedWord())
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un banner")
                .redirectTo("/banners/" + newOffer.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El banner se creo con exito")
            .data(newOffer)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid OfferRequest request, @PathVariable Long id) {
        OfferDTO offerToUpdate = offerService.findById(id);
        offerToUpdate.setTitle(request.getTitle());
        offerToUpdate.setDescription(request.getDescription());
        offerToUpdate.setIsUsed(request.getIsUsed());
        offerToUpdate.setMarkedWord(request.getMarkedWord());
        OfferDTO offerUpdated = offerService.save(offerToUpdate);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un banner")
                .redirectTo("/banners/" + offerUpdated.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El banner se actualizo con exito")
            .data(offerUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        offerService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El banner se elimino con exito")
            .build());
    }
}
