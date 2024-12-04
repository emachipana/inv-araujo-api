package com.inversionesaraujo.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.InvoiceType;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.model.payload.FileResponse;

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
