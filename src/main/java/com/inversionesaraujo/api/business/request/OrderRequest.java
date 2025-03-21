package com.inversionesaraujo.api.business.request;

import java.time.LocalDate;

import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "El id del cliente es requerido")
    private Long clientId;

    @NotEmpty(message = "El departamento es requerido")
    private String department;

    @NotEmpty(message = "La ciudad es requerida")
    private String city;
    
    private Status status = Status.PENDIENTE;
    
    private OrderLocation location = OrderLocation.ALMACEN;
    
    @NotNull(message = "El tipo de entrega es requerido")
    private ShippingType shippingType;

    private LocalDate date;
    private Long invoiceId;
    private Long warehouseId;
    private Long imageId;
    private Long employeeId;
}
