package com.inversionesaraujo.api.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyProductionDTO {
    private Integer year;
    private Integer month;
    private List<VarietyQuantityDTO> varieties;
}
