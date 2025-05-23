package com.inversionesaraujo.api.business.dto;

import java.util.List;

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
public class InvoiceBodyDTO {
    String ublVersion;
    String tipoOperacion;
    // tipo de doc: FACTURA - 01 / BOLETA - 03
    String tipoDoc;
    String serie;
    String correlativo;
    String fechaEmision;
    InvoicePayDTO formaPago;
    String tipoMoneda;
    InvoiceClientDTO client;
    InvoiceCompanyDTO company;
    Double mtoOperGravadas;
    Double mtoIGV;
    Double valorVenta;
    Double totalImpuestos;
    Double subTotal;
    Double mtoImpVenta;
    List<InvoiceDetailDTO> details;
}
