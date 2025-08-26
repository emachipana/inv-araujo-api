package com.inversionesaraujo.api.business.request;

import java.time.LocalDate;

import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.ShippingType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VitroOrderRequest {
    @NotNull(message = "El id del cliente es requerido")
    private Long clientId;

    @Size(min = 3)
    private String department;

    @Size(min = 3)
    private String city;

    private Status status = Status.PENDIENTE;
    private OrderLocation location = OrderLocation.ALMACEN;

    private ShippingType shippingType;
    private Boolean isReady = false;
    private LocalDate initDate;
    private LocalDate finishDate;
    private Long operatorId;
    private String createdBy = "CLIENTE";
}
