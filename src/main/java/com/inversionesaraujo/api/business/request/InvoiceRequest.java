package com.inversionesaraujo.api.business.request;

import java.time.LocalDate;

import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.InvoiceType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class InvoiceRequest {
    @NotNull(message = "El tipo de comprobante es requerido")
    private InvoiceType invoiceType;

    @NotNull(message = "El tipo de documento es requerido")
    private DocumentType documentType;

    @NotEmpty(message = "El documento es requerido")
    @Size(min = 8)
    private String document;

    @NotEmpty(message = "La razón social es requerida")
    private String rsocial;

    private LocalDate issueDate;
    private String comment;
    private String address;
}
