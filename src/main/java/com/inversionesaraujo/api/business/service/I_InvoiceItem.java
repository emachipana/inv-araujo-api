package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.InvoiceItemDTO;

public interface I_InvoiceItem {
    InvoiceItemDTO save(InvoiceItemDTO item);
    
    InvoiceItemDTO findById(Long id);

    List<InvoiceItemDTO> findByInvoiceId(Long invoiceId);

    Integer countByInvoiceId(Long invoiceId);

    void delete(Long id);
}
