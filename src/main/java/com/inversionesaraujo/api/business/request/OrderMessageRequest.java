package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMessageRequest {
    @NotNull(message = "El id de usuario que envia el mensaje es requerido")
    private Long senderId;

    @NotNull(message = "El id de usuario que recibe el mensaje es requerido")
    private Long receiverId;
    
    @NotNull(message = "El id del pedido es requerido")
    private Long orderId;
    
    @NotEmpty(message = "El mensaje es requerido")
    private String message;
}
