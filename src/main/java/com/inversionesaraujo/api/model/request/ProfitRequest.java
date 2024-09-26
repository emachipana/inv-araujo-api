package com.inversionesaraujo.api.model.request;

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
    private Integer adminId;
    private LocalDate date;
}
