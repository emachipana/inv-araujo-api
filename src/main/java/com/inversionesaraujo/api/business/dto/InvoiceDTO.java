package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.InvoiceType;

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
public class InvoiceDTO {
    private Long id;
    private InvoiceType invoiceType;
    private String document;
    private DocumentType documentType;
    private String rsocial;
    private LocalDateTime issueDate;
    private String address;
    private String serie;
    private Boolean isSended;
    private Double total;
    private String pdfUrl;
    private Boolean isRelatedToOrder;

    public static InvoiceDTO toDTO(Invoice invoice) {
        if(invoice == null) return null;

        return InvoiceDTO
            .builder()
            .id(invoice.getId())
            .invoiceType(invoice.getInvoiceType())
            .document(invoice.getDocument())
            .documentType(invoice.getDocumentType())
            .rsocial(invoice.getRsocial())
            .issueDate(invoice.getIssueDate())
            .address(invoice.getAddress())
            .serie(invoice.getSerie())
            .isSended(invoice.getIsSended())
            .total(invoice.getTotal())
            .pdfUrl(invoice.getPdfUrl())
            .isRelatedToOrder(invoice.getIsRelatedToOrder())
            .build();
    }

    public static Invoice toEntity(InvoiceDTO invoice) {
        if(invoice == null) return null;

        return Invoice
            .builder()
            .id(invoice.getId())
            .invoiceType(invoice.getInvoiceType())
            .documentType(invoice.getDocumentType())
            .rsocial(invoice.getRsocial())
            .issueDate(invoice.getIssueDate())
            .document(invoice.getDocument())
            .pdfUrl(invoice.getPdfUrl())
            .address(invoice.getAddress())
            .serie(invoice.getSerie())
            .isSended(invoice.getIsSended())
            .total(invoice.getTotal())
            .isRelatedToOrder(invoice.getIsRelatedToOrder())
            .build();
    }

    public static Page<InvoiceDTO> toPageableDTO(Page<Invoice> invoices) {
        return invoices.map(InvoiceDTO::toDTO);
    }
}
