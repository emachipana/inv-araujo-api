package com.inversionesaraujo.api.business.dto;

import java.time.LocalDate;

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
    private LocalDate issueDate;
    private String comment;
    private String pdfUrl;
    private String address;
    private String serie;
    private Boolean isGenerated;
    private Double total;
    private String pdfFirebaseId;

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
            .comment(invoice.getComment())
            .pdfUrl(invoice.getPdfUrl())
            .address(invoice.getAddress())
            .serie(invoice.getSerie())
            .isGenerated(invoice.getIsGenerated())
            .total(invoice.getTotal())
            .pdfFirebaseId(invoice.getPdfFirebaseId())
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
            .comment(invoice.getComment())
            .document(invoice.getDocument())
            .pdfUrl(invoice.getPdfUrl())
            .address(invoice.getAddress())
            .serie(invoice.getSerie())
            .pdfFirebaseId(invoice.getPdfFirebaseId())
            .isGenerated(invoice.getIsGenerated())
            .total(invoice.getTotal())
            .build();
    }

    public static Page<InvoiceDTO> toPageableDTO(Page<Invoice> invoices) {
        return invoices.map(InvoiceDTO::toDTO);
    }
}
