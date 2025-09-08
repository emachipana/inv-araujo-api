package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.PaymentType;
import com.inversionesaraujo.api.model.Status;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest {
    @NotNull(message = "El estado es requerido")
    private Status status;
    
    private PaymentType paymentType;
}
