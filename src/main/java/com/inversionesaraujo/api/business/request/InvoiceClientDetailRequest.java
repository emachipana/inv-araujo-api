package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.InvoiceType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceClientDetailRequest {
    @NotEmpty(message = "El documento es requerido")
    private String document;

    @NotNull(message = "El tipo de documento es requerido")
    private DocumentType documentType;

    @NotEmpty(message = "La razón social es requerida")
    private String rsocial;

    @NotEmpty(message = "La dirección es requerida")
    private String address;

    @NotNull(message = "El comprobante preferido es requerido")
    private InvoiceType invoicePreference;
}
