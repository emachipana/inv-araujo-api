package com.inversionesaraujo.api.business.dto.request;

import java.time.LocalDate;

import com.google.auto.value.AutoValue.Builder;
import com.inversionesaraujo.api.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer clientId;
    private Integer invoiceId;
    private String department;
    private String city;
    private Status status;
    private LocalDate date;
}
