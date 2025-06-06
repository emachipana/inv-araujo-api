package com.inversionesaraujo.api.business.request;

import com.inversionesaraujo.api.model.DocumentType;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientRequest {
    @Size(min = 3)
    private String city;

    @Size(min = 3)
    private String department;

    @Size(min = 6)
    private String phone;

    @NotNull(message = "El tipo de documento es requerido")
    private DocumentType documentType;

    @Size(min = 8)
    private String document;

    @NotEmpty(message = "La razón social es requerida")
    @Size(min = 3)
    private String rsocial;

    @NotNull(message = "El origen de creación es requerido")
    private Role createdBy;

    @NotEmpty(message = "El email es requerido")
    @Email(message = "El formato es incorrecto")
    private String email;

    @NotNull(message = "El comprobante preferido es requerido")
    private InvoiceType invoicePreference;

    private Long userId;
}
