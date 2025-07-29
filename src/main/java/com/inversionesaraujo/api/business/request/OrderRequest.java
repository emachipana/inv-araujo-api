package com.inversionesaraujo.api.business.request;

import java.time.LocalDate;

import com.inversionesaraujo.api.business.dto.PickupInfoDTO;
import com.inversionesaraujo.api.business.dto.ReceiverInfoDTO;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.Status;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "El id del cliente es requerido")
    private Long clientId;

    @NotNull(message = "El tipo de entrega es requerido")
    private ShippingType shippingType;
    
    private OrderLocation location = OrderLocation.ALMACEN;
    private String department;
    private String city;
    private Status status = Status.PENDIENTE;
    private LocalDate date;
    private Long warehouseId;
    private Long employeeId;
    private ReceiverInfoDTO receiverInfo;
    private PickupInfoDTO pickupInfo;
}
