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
public class InvoiceDetailDTO {
    String codProducto;
    String unidad;
    String descripcion;
    Integer cantidad;
    Double mtoValorUnitario;
    Double mtoValorVenta;
    Double mtoBaseIgv;
    Integer porcentajeIgv;
    Double igv;
    Integer tipAfeIgv;
    Double totalImpuestos;
    Double mtoPrecioUnitario;
}
