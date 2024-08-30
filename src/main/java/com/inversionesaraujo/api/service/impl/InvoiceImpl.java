package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.InvoiceDao;
import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.service.I_Invoice;

@Service
public class InvoiceImpl implements I_Invoice {
    @Autowired
    private InvoiceDao invoiceRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Invoice> listAll() {
        return invoiceRepo.findAll();
    }

    @Transactional
    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

    @Transactional(readOnly = true)
    @Override
    public Invoice findById(Integer id) {
        return invoiceRepo.findById(id).orElseThrow(() -> new DataAccessException("El usuario no existe") {});
    }

    @Transactional
    @Override
    public void delete(Invoice invoice) {
        invoiceRepo.delete(invoice);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return invoiceRepo.existsById(id);
    }
}
