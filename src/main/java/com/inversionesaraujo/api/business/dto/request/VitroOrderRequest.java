package com.inversionesaraujo.api.business.dto.request;

import java.time.LocalDate;

import com.google.auto.value.AutoValue.Builder;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VitroOrderRequest {
    private Integer clientId;
    private String department;
    private String city;
    private LocalDate initDate;
    private LocalDate finishDate;
    private Status status;
    private Integer invoiceId;
    private OrderLocation location;
}
