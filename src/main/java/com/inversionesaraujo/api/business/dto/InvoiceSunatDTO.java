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
public class InvoiceSunatDTO {
    String operacion;
    Integer tipo_de_comprobante; // 1: Factura, 2: Boleta
    String serie;
    Integer numero; // correlativo
    Integer sunat_transaction;
    Integer cliente_tipo_de_documento; // 6: RUC, 1: DNI
    String cliente_numero_de_documento;
    String cliente_denominacion; // rsocial
    String cliente_direccion;
    String fecha_de_emision; // yyyy-MM-dd
    Integer moneda; // 1: soles
    Double porcentaje_de_igv;
    Double total_gravada; // total sin IGV
    Double total_igv;
    Double total;
    Boolean detraccion;
    Boolean enviar_automaticamente_a_la_sunat;
    Boolean enviar_automaticamente_al_cliente;
    List<InvoiceDetailSunatDTO> items;
}
