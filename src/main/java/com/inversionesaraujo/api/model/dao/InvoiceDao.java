package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {}
