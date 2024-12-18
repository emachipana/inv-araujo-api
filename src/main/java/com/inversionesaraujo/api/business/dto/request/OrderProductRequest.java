package com.inversionesaraujo.api.business.dto.request;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductRequest {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
}
