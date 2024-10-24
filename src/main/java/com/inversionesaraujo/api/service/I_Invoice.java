package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.InvoiceType;
import com.inversionesaraujo.api.model.payload.FileResponse;

public interface I_Invoice {
    List<Invoice> listAll();

    Invoice save(Invoice invoice);

    Invoice findById(Integer id);

    void delete(Invoice invoice);

    FileResponse generateAndUploadPDF(Invoice invoice);

    List<Invoice> findByInvoiceType(InvoiceType type);
}
