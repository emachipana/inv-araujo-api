package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.I_InvoiceItem;
import com.inversionesaraujo.api.model.InvoiceItem;
import com.inversionesaraujo.api.repository.InvoiceItemRepository;

@Service
public class InvoiceItemImpl implements I_InvoiceItem {
    @Autowired
    private InvoiceItemRepository itemRepo;

    @Transactional
    @Override
    public InvoiceItem save(InvoiceItem item) {
        return itemRepo.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public InvoiceItem findById(Integer id) {
        return itemRepo.findById(id).orElseThrow(() -> new DataAccessException("El item de la factura no existe") {});
    }

    @Transactional
    @Override
    public void delete(InvoiceItem item) {
        itemRepo.delete(item);
    }
}
