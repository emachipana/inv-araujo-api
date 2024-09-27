package com.inversionesaraujo.api.model.request;

import java.time.LocalDate;

import com.google.auto.value.AutoValue.Builder;
import com.inversionesaraujo.api.model.entity.PayType;
import com.inversionesaraujo.api.model.entity.ShipType;
import com.inversionesaraujo.api.model.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer clientId;
    private ShipType shipType;
    private PayType payType;
    private Integer invoiceId;
    private String destination;
    private Status status;
    private LocalDate date;
}
