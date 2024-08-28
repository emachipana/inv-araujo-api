package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.InvoiceItem;

public interface InvoiceItemDao extends JpaRepository<InvoiceItem, Integer> {}
