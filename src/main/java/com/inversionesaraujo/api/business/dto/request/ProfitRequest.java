package com.inversionesaraujo.api.business.dto.request;

import java.time.LocalDate;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfitRequest {
    private LocalDate date;
    private Double income;
}
