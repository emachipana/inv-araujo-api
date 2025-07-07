package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.InvoiceClientDetailDTO;

public interface I_InvoiceClientDetail {
    InvoiceClientDetailDTO save(InvoiceClientDetailDTO invoiceClientDetail);
    
    InvoiceClientDetailDTO findById(Long id);
    
    void delete(Long id);
}
