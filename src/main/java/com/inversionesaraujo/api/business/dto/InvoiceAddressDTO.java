package com.inversionesaraujo.api.business.dto;

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
public class InvoiceAddressDTO {
    String direccion;
    String provincia;
    String departamento;
    String distrito;
    String ubigueo;
}
