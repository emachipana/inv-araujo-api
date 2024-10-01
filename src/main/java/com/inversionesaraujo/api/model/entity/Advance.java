package com.inversionesaraujo.api.model.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "advances")
public class Advance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn
  @NotNull(message = "El id del pedido invitro no puede ir vacio")
  @JsonIgnore
  private VitroOrder vitroOrder;
  @NotNull(message = "El monto no puede ir vacio")
  private Double amount;
  @NotNull(message = "La fecha del adelanto no puede ir vacio")
  private LocalDate date;
}
