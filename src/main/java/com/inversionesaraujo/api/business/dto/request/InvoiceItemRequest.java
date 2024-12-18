package com.inversionesaraujo.api.business.dto.request;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemRequest {
    private Integer invoiceId;
    private String name;
    private String itemCode;
    private Double price;
    private Integer quantity;
    private boolean isIgvApply = true;
}
