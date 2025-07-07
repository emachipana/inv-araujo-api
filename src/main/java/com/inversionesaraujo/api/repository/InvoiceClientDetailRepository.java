package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.InvoiceClientDetail;

public interface InvoiceClientDetailRepository extends JpaRepository<InvoiceClientDetail, Long> {}
