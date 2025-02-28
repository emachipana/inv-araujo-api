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

import com.inversionesaraujo.api.business.dto.TuberDTO;
import com.inversionesaraujo.api.business.dto.VarietyDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.VarietyRequest;
import com.inversionesaraujo.api.business.service.ITuber;
import com.inversionesaraujo.api.business.service.IVariety;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/varieties")
public class VarietyController {
    @Autowired
    private IVariety varietyService;
    @Autowired
    private ITuber tuberService;

    @GetMapping("tuber/{tuberId}")
    public List<VarietyDTO> getByTuberId(@PathVariable Long tuberId) {
        return varietyService.findByTuberId(tuberId);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        VarietyDTO variety = varietyService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La variedad se encontro con exito")
            .data(variety)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid VarietyRequest request) {
        TuberDTO tuber = tuberService.findById(request.getTuberId());
        
        VarietyDTO variety = varietyService.save(VarietyDTO
            .builder()
            .tuberId(request.getTuberId())
            .tuberName(tuber.getName())
            .price(request.getPrice())
            .name(request.getName())
            .minPrice(request.getMinPrice())
            .build());
        
        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("La variedad se creo con exito")
            .data(variety)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid VarietyRequest request) {
        VarietyDTO variety = varietyService.findById(id);
        variety.setName(request.getName());
        variety.setPrice(request.getPrice());
        variety.setMinPrice(request.getMinPrice());
        VarietyDTO varietyUpdated = varietyService.save(variety);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La variedad se actualizo con exito")
            .data(varietyUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        varietyService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La variedad se elimino con exito")
            .build());
    }
}
