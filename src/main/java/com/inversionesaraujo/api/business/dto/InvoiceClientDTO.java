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
public class InvoiceClientDTO {
    // tipo doc identidad: DNI - 1 / RUC - 6
    String tipoDoc;
    Long numDoc;
    String rznSocial;
    InvoiceAddressDTO address;
}
