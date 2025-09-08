package com.inversionesaraujo.api.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VarietyQuantityDTO {
    private String tuber;
    private String variety;
    private Integer quantity;
}
