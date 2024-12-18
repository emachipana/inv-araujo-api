package com.inversionesaraujo.api.business.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.payload.FileResponse;
import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.SortDirection;

public interface I_Invoice {
    Page<Invoice> listAll(
        InvoiceType type, Integer pag, Integer size, 
        SortDirection direction
    );

    Invoice save(Invoice invoice);

    Invoice findById(Integer id);

    void delete(Invoice invoice);

    FileResponse generateAndUploadPDF(Invoice invoice);

    List<Invoice> search(String rsocial, String document);
}
