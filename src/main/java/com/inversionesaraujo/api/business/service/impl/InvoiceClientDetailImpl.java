package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.InvoiceClientDetailDTO;
import com.inversionesaraujo.api.business.service.I_InvoiceClientDetail;
import com.inversionesaraujo.api.model.InvoiceClientDetail;
import com.inversionesaraujo.api.repository.InvoiceClientDetailRepository;

import jakarta.persistence.EntityManager;

@Service
public class InvoiceClientDetailImpl implements I_InvoiceClientDetail {
    @Autowired
    private InvoiceClientDetailRepository repo;
    @Autowired
    private EntityManager entityManager;
  
    @Override
    public InvoiceClientDetailDTO save(InvoiceClientDetailDTO invoiceClientDetail) {
        InvoiceClientDetail invoiceClientDetailSaved = repo.save(InvoiceClientDetailDTO.toEntity(invoiceClientDetail, entityManager));

        return InvoiceClientDetailDTO.toDTO(invoiceClientDetailSaved);
    }
    
    @Override
    public InvoiceClientDetailDTO findById(Long id) {
        InvoiceClientDetail invoiceClientDetail = repo.findById(id).orElseThrow(() -> new DataAccessException("El detalle de facturación del cliente no existe") {});
        return InvoiceClientDetailDTO.toDTO(invoiceClientDetail);
    }
    
    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
