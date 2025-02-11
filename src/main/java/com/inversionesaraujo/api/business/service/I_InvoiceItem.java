package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.InvoiceItemDTO;

public interface I_InvoiceItem {
    InvoiceItemDTO save(InvoiceItemDTO item);
    
    InvoiceItemDTO findById(Long id);

    void delete(Long id);
}
