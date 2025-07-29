package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.OrderMessageDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.OrderMessageRequest;
import com.inversionesaraujo.api.business.service.IOrderMessage;
import com.inversionesaraujo.api.business.service.IUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order-message")
public class OrderMessageController {
    @Autowired
    private IOrderMessage orderMessageService;
    @Autowired
    private IUser userService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody @Valid OrderMessageRequest request) {
        UserDTO sender = userService.findById(request.getSenderId());
        UserDTO receiver = userService.findById(request.getReceiverId());

        OrderMessageDTO orderMessage = orderMessageService.save(
            OrderMessageDTO
            .builder()
            .orderId(request.getOrderId())
            .message(request.getMessage())
            .sender(sender)
            .receiver(receiver)
            .build()
        );

        orderMessageService.alertNewMessage(orderMessage);
        return ResponseEntity.ok().body(
            MessageResponse.builder()
            .message("Mensaje almacenado y enviado correctamente")
            .data(orderMessage)
            .build()
        );
    }
}
