package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.InvoiceItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDTO {
    private Long id;
    private Long invoiceId;
    private String name;
    private Integer quantity;
    private Double price;
    private Double subTotal;
    private Boolean isIgvApply;

    public static InvoiceItemDTO toDTO(InvoiceItem item) {
        return InvoiceItemDTO
            .builder()
            .id(item.getId())
            .invoiceId(item.getInvoice().getId())
            .name(item.getName())
            .quantity(item.getQuantity())
            .price(item.getPrice())
            .subTotal(item.getSubTotal())
            .isIgvApply(item.getIsIgvApply())
            .build();
    }

    public static InvoiceItem toEntity(InvoiceItemDTO item) {
        Invoice invoice = new Invoice();
        invoice.setId(item.getInvoiceId());
        
        return InvoiceItem
            .builder()
            .id(item.getId())
            .invoice(invoice)
            .name(item.getName())
            .quantity(item.getQuantity())
            .price(item.getPrice())
            .subTotal(item.getSubTotal())
            .isIgvApply(item.getIsIgvApply())
            .build();
    }
}
