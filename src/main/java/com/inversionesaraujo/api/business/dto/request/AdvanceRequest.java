package com.inversionesaraujo.api.business.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceRequest {
    private Integer vitroOrderId;
    private Double amount;
    private LocalDate date;
}
