package com.inversionesaraujo.api.model.spec;

import org.springframework.data.jpa.domain.Specification;

import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.InvoiceType;

public class InvoiceSpecifications {
    public static Specification<Invoice> findByInvoiceType(InvoiceType type) {
        return (root, query, criteriaBuilder) ->
            type != null ? criteriaBuilder.equal(root.get("invoiceType"), type) : null;
    }
}
