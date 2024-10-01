package com.inversionesaraujo.api.model.request;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VarietyRequest {
    private Double price;
    private Double minPrice;
    private String name;
    private Integer tuberId;
}
