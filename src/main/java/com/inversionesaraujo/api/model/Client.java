package com.inversionesaraujo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String department;

    private String phone;

    @Column(unique = true)
    private String document;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    
    @Column(nullable = false)
    @Builder.Default
    private Double consumption = 0.0;

    @Column(nullable = false)
    private String rsocial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role createdBy = Role.CLIENTE;

    @Column(unique = true, nullable = false)
    private String email;
    
    @OneToOne(mappedBy = "client")
    private User user;
}
