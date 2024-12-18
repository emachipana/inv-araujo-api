package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.inversionesaraujo.api.model.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
    List<Invoice> findByrSocialContainingIgnoreCaseOrDocumentContainingIgnoreCase(String rsocial, String document);
}
