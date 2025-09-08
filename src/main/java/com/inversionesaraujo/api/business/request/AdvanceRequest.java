package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.PaymentType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvanceRequest {
    @NotNull(message = "El id del pedido invitro es requerido")
    private Long vitroOrderId;

    @NotNull(message = "El monto del adelanto es requerido")
    @Positive(message = "Debe ser mayor a 0")
    private Double amount;

    @NotNull(message = "El tipo de pago es requerido")
    private PaymentType paymentType;

    private Long employeeId;
}
