package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.InvoiceClientDetail;
import com.inversionesaraujo.api.model.InvoiceType;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceClientDetailDTO {
    private Long id;
    private String document;
    private DocumentType documentType;
    private String rsocial;
    private String address;
    private InvoiceType invoicePreference;
    private Long clientId;

    public static InvoiceClientDetailDTO toDTO(InvoiceClientDetail invoiceClientDetail) {
        if(invoiceClientDetail == null) return null;

        return InvoiceClientDetailDTO
            .builder()
            .id(invoiceClientDetail.getId())
            .document(invoiceClientDetail.getDocument())
            .documentType(invoiceClientDetail.getDocumentType())
            .rsocial(invoiceClientDetail.getRsocial())
            .address(invoiceClientDetail.getAddress())
            .invoicePreference(invoiceClientDetail.getInvoicePreference())
            .clientId(invoiceClientDetail.getClient().getId())
            .build();
    }

    public static InvoiceClientDetail toEntity(InvoiceClientDetailDTO invoiceClientDetail, EntityManager entityManager) {
        if(invoiceClientDetail == null) return null;

        Client client = entityManager.getReference(Client.class, invoiceClientDetail.getClientId());

        return InvoiceClientDetail
            .builder()
            .id(invoiceClientDetail.getId())
            .document(invoiceClientDetail.getDocument())
            .documentType(invoiceClientDetail.getDocumentType())
            .rsocial(invoiceClientDetail.getRsocial())
            .address(invoiceClientDetail.getAddress())
            .invoicePreference(invoiceClientDetail.getInvoicePreference())
            .client(client)
            .build();
    }
}
