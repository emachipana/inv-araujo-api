package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.InvoiceItem;

public interface I_InvoiceItem {
    InvoiceItem save(InvoiceItem item);
    
    InvoiceItem findById(Integer id);

    void delete(InvoiceItem item);
}
