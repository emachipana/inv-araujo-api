package com.inversionesaraujo.api.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.InvoiceItemDao;
import com.inversionesaraujo.api.model.entity.InvoiceItem;
import com.inversionesaraujo.api.service.I_InvoiceItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceItemImpl implements I_InvoiceItem {
    private InvoiceItemDao itemDao;

    @Transactional
    @Override
    public InvoiceItem save(InvoiceItem item) {
        return itemDao.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public InvoiceItem findById(Integer id) {
        return itemDao.findById(id).orElseThrow(() -> new DataAccessException("El item de la factura no existe") {});
    }

    @Transactional
    @Override
    public void delete(InvoiceItem item) {
        itemDao.delete(item);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return itemDao.existsById(id);
    }
}
