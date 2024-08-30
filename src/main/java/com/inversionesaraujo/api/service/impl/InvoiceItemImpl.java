package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.InvoiceItemDao;
import com.inversionesaraujo.api.model.entity.InvoiceItem;
import com.inversionesaraujo.api.service.I_InvoiceItem;

@Service
public class InvoiceItemImpl implements I_InvoiceItem {
    @Autowired
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
}
