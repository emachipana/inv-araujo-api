package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.inversionesaraujo.api.model.entity.Invoice;

import java.util.List;

public interface InvoiceDao extends JpaRepository<Invoice, Integer>, JpaSpecificationExecutor<Invoice> {
    List<Invoice> findByrSocialContainingIgnoreCaseOrDocumentContainingIgnoreCase(String rsocial, String document);
}
