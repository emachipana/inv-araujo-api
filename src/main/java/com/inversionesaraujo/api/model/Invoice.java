package com.inversionesaraujo.api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String rsocial;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    private String pdfUrl;
    private String pdfFirebaseId;
    private String address;
    private String serie;

    @Builder.Default
    private Boolean isGenerated = false;

    @Column(nullable = false)
    private Double total;
}
