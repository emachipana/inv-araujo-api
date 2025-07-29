package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.InvoiceType;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAuthRequest {
    @NotEmpty(message = "El token de Google es requerido")
    private String token;

    private DocumentType documentType;
    private String document;
    private String rsocial;
    private InvoiceType invoicePreference;
}
