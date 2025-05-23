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
public class InvoiceCompanyDTO {
    Long ruc;
    String razonSocial;
    String nombreComercial;
    InvoiceAddressDTO address;
}
