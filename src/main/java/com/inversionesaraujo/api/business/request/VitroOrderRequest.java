package com.inversionesaraujo.api.business.request;

import java.time.LocalDate;

import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.ShippingType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitroOrderRequest {
    @NotNull(message = "El id del cliente es requerido")
    private Long clientId;

    @NotEmpty(message = "El departamento es requerido")
    @Size(min = 3)
    private String department;

    @NotEmpty(message = "La ciudad es requerida")
    @Size(min = 3)
    private String city;

    
    private Status status = Status.PENDIENTE;
    private OrderLocation location = OrderLocation.ALMACEN;

    @NotNull(message = "El tipo de envío es requerido")
    private ShippingType shippingType;

    private LocalDate initDate;
    private LocalDate finishDate;
    private Long invoiceId;
}
