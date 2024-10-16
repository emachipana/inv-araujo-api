package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.InvoiceType;

import java.util.List;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByInvoiceType(InvoiceType invoiceType);
}
