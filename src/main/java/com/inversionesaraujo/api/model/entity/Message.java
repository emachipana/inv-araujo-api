package com.inversionesaraujo.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "El nombre no puede ir vacio")
    @Size(min = 5, max = 150)
    private String fullName;
    @NotEmpty(message = "El telefono no puede ir vacio")
    @Size(max = 12)
    private String phone;
    @NotEmpty(message = "El asunto no puede ir vacio")
    @Size(min = 3, max = 50)
    private String subject;
    @NotEmpty(message = "El contenido del mensaje no puede ir vacio")
    @Size(min = 5)
    @Column(columnDefinition = "TEXT")
    private String content;
    @NotNull(message = "El origen no pueder ir vacio")
    @Enumerated(EnumType.STRING)
    private Origin origin;
    @NotEmpty(message = "El email no puede ir vacio")
    @Email(message = "El formato es incorrecto")
    private String email;
}
