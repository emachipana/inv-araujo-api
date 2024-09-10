package com.inversionesaraujo.api.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  
    @NotNull(message = "El tipo de comprobante no puede ir vacio")
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    @NotNull(message = "El tipo de documento no puede ir vacio")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @NotEmpty(message = "El documento no puede ir vacio")
    @Size(max = 20)
    private String document;
    @NotEmpty(message = "La razon social no puede ir vacia")
    @Size(min = 3, max = 200)
    private String rSocial;
    @Column(nullable = false)
    private LocalDateTime issueDate;
    @Size(min = 5)
    @Column(columnDefinition = "TEXT")
    private String comment;
    private String pdfUrl;
    private String pdfFirebaseId;
    @NotEmpty(message = "La direccion no puede ir vacia")
    private String address;
    @Column(nullable = false)
    private Double total;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceItem> items;
}
