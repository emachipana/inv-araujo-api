package com.inversionesaraujo.api.business.service;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.payload.ApiSunatResponse;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.SortDirection;

public interface I_Invoice {
    Page<InvoiceDTO> listAll(
        InvoiceType type, Integer pag, Integer size, 
        SortDirection direction
    );

    InvoiceDTO save(InvoiceDTO invoice);

    InvoiceDTO findById(Long id);

    void delete(Long id);

    ApiSunatResponse sendInvoiceToSunat(InvoiceDTO invoice);

    Page<InvoiceDTO> search(String rsocial, String document, Integer page);
}
