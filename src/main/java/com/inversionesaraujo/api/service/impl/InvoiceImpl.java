package com.inversionesaraujo.api.service.impl;

import java.util.List;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.inversionesaraujo.api.model.dao.InvoiceDao;
import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.InvoiceType;
import com.inversionesaraujo.api.model.payload.FileResponse;
import com.inversionesaraujo.api.service.I_Invoice;

@Service
public class InvoiceImpl implements I_Invoice {
    @Autowired
    private InvoiceDao invoiceRepo;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Transactional(readOnly = true)
    @Override
    public List<Invoice> listAll() {
        return invoiceRepo.findAll();
    }

    @Transactional
    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

    @Transactional(readOnly = true)
    @Override
    public Invoice findById(Integer id) {
        return invoiceRepo.findById(id).orElseThrow(() -> new DataAccessException("El comprobante no existe") {});
    }

    @Transactional
    @Override
    public void delete(Invoice invoice) {
        invoiceRepo.delete(invoice);
    }

    @Override
    public FileResponse generateAndUploadPDF(Invoice invoice) {
        Context context = new Context();
        context.setVariable("invoiceType", invoice.getInvoiceType());   
        context.setVariable("clientName", invoice.getRSocial());   
        context.setVariable("documentType", invoice.getDocumentType());
        context.setVariable("document", invoice.getDocument());
        context.setVariable("address", invoice.getAddress());
        context.setVariable("total", invoice.getTotal());
        context.setVariable("items", invoice.getItems());

        String htmlContent = templateEngine.process("invoice", context);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(byteOutput);
        byte[] pdfBytes = byteOutput.toByteArray();

        Bucket bucket = StorageClient.getInstance().bucket();
        String fileName = UUID.randomUUID().toString();
        bucket.create(fileName, pdfBytes, "application/pdf");
        String pdfUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), fileName);

        return FileResponse
            .builder()
            .fileUrl(pdfUrl)
            .fileName(fileName)
            .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Invoice> findByInvoiceType(InvoiceType type) {
        return invoiceRepo.findByInvoiceType(type);
    }
}
