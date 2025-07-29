package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.CancelOrderRequestDTO;
import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.CancelRequest;
import com.inversionesaraujo.api.business.service.ICancelOrderRequest;
import com.inversionesaraujo.api.business.service.IOrder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cancel-order")
public class CancelOrderRequestController {
    @Autowired
    private ICancelOrderRequest cancelService;
    @Autowired
    private IOrder orderService;

    @GetMapping("/order/{orderId}")
    public List<CancelOrderRequestDTO> getOneByOrder(@PathVariable Long orderId) {
        return cancelService.findByOrderId(orderId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getOne(@PathVariable Long id) {
        CancelOrderRequestDTO cancelOrderRequest = cancelService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La solicitud se encontro con exito")
            .data(cancelOrderRequest)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid CancelRequest cancelRequest) {
        OrderDTO order = orderService.findById(cancelRequest.getOrderId());

        CancelOrderRequestDTO cancelOrderRequest = cancelService.save(CancelOrderRequestDTO.builder()
            .reason(cancelRequest.getReason())
            .orderId(order.getId())
            .accepted(false)
            .rejected(false)
            .build());

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La solicitud se creó con exito")
            .data(cancelOrderRequest)
            .build());
    }

    @PutMapping("{id}/accept")
    public ResponseEntity<MessageResponse> accept(@PathVariable Long id) {
        CancelOrderRequestDTO cancelOrderRequest = cancelService.findById(id);

        cancelService.acceptRequest(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La solicitud se aceptó con exito")
            .data(cancelOrderRequest)
            .build());
    }

    @PutMapping("{id}/reject")
    public ResponseEntity<MessageResponse> reject(@PathVariable Long id) {
        CancelOrderRequestDTO cancelOrderRequest = cancelService.findById(id);

        cancelService.rejectRequest(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("La solicitud se rechazó con exito")
            .data(cancelOrderRequest)
            .build());
    }
}
