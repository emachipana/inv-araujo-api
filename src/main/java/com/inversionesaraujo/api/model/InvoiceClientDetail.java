package com.inversionesaraujo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "invoice_client_details")
public class InvoiceClientDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_client_detail_seq")
    @SequenceGenerator(name = "invoice_client_detail_seq", sequenceName = "invoice_client_detail_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String document;

    @Column(nullable = false)
    private String rsocial;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private InvoiceType invoicePreference;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
