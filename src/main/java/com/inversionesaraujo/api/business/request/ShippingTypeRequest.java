package com.inversionesaraujo.api.business.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.inversionesaraujo.api.business.dto.PickupInfoDTO;
import com.inversionesaraujo.api.business.dto.ReceiverInfoDTO;
import com.inversionesaraujo.api.model.ShippingType;

@Getter
@Setter
public class ShippingTypeRequest {
    @NotNull(message = "El tipo de envio es requerido")
    private ShippingType shippingType;

    private String department;
    private String city;
    private ReceiverInfoDTO receiverInfo;
    private PickupInfoDTO pickupInfo;
}
