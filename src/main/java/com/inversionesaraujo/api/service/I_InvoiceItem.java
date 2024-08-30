package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.InvoiceItem;

public interface I_InvoiceItem {
    InvoiceItem save(InvoiceItem item);
    
    InvoiceItem findById(Integer id);

    void delete(InvoiceItem item);

    boolean ifExists(Integer id);
}
