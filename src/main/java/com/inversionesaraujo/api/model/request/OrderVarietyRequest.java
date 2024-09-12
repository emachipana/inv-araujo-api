package com.inversionesaraujo.api.model.request;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderVarietyRequest {
    private Integer vitroOrderId;
    private Integer varietyId;
    private Double price;
    private Integer quantity;
}