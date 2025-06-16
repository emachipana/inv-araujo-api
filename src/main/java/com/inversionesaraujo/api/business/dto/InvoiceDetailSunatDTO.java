package com.inversionesaraujo.api.business.dto;

import com.inversionesaraujo.api.model.ProductUnit;

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
public class InvoiceDetailSunatDTO {
    ProductUnit unidad_de_medida;
    String codigo;
    String descripcion;
    Integer cantidad;
    Double valor_unitario; // precio sin IGV
    Double precio_unitario; // precio con IGV
    Double subtotal; // valor_unitario * cantidad
    Integer tipo_de_igv;
    Double igv;
    Double total; // subtotal + igv
    Boolean anticipo_regularizacion;
}
