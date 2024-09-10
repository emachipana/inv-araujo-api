package com.inversionesaraujo.api.model.request;

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
    private Integer productId;
    private Integer quantity;
    private boolean isIgvApply = true;
}
