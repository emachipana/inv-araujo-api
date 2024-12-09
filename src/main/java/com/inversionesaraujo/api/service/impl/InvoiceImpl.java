package com.inversionesaraujo.api.service.impl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.inversionesaraujo.api.helpers.Capitalize;
import com.inversionesaraujo.api.model.dao.InvoiceDao;
import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.InvoiceType;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.model.payload.FileResponse;
import com.inversionesaraujo.api.model.spec.InvoiceSpecifications;
import com.inversionesaraujo.api.service.I_Invoice;

@Service
public class InvoiceImpl implements I_Invoice {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private TemplateEngine templateEngine;

    @Transactional(readOnly = true)
    @Override
    public Page<Invoice> listAll(InvoiceType type, Integer page, Integer size, SortDirection direction) {
        Specification<Invoice> spec = Specification.where(InvoiceSpecifications.findByInvoiceType(type));
        Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "issueDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        return invoiceDao.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public Invoice save(Invoice invoice) {
        return invoiceDao.save(invoice);
    }

    @Transactional(readOnly = true)
    @Override
    public Invoice findById(Integer id) {
        return invoiceDao.findById(id).orElseThrow(() -> new DataAccessException("El comprobante no existe") {});
    }

    @Transactional
    @Override
    public void delete(Invoice invoice) {
        invoiceDao.delete(invoice);
    }

    @Override
    public FileResponse generateAndUploadPDF(Invoice invoice) {
        Context context = new Context();
        LocalDate date = invoice.getIssueDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        context.setVariable("issueDate", date.format(formatter));
        context.setVariable("invoiceType", invoice.getInvoiceType());  
        context.setVariable("serie", (invoice.getInvoiceType() == InvoiceType.BOLETA ? "B-" : "F-") + invoice.getId());
        context.setVariable("rsocial", Capitalize.capitalizeEachWord(invoice.getRSocial()));   
        context.setVariable("document", invoice.getDocumentType() + ":" + " " + invoice.getDocument());
        String address = invoice.getAddress().isEmpty() ? "-" : Capitalize.capitalizeEachWord(invoice.getAddress());
        context.setVariable("address", address);
        context.setVariable("items", invoice.getItems());
        String comment = invoice.getComment().isEmpty() ? "-" : Capitalize.capitalizeEachWord(invoice.getComment());
        context.setVariable("comment", comment);
        Double total = invoice.getTotal();
        Double base = total / 1.18;
        Double igv = base * 0.18;
        DecimalFormat df = new DecimalFormat("#.00");
        context.setVariable("base", df.format(base));
        context.setVariable("igv", df.format(igv));
        context.setVariable("total", invoice.getTotal());

        String htmlContent = templateEngine.process("invoice", context);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(byteOutput);
        byte[] pdfBytes = byteOutput.toByteArray();

        Bucket bucket = StorageClient.getInstance().bucket();
        String type = invoice.getInvoiceType().toString();
        String fileName = type.toLowerCase() + "-" + invoice.getId() + "-" + invoice.getRSocial().toLowerCase().replaceAll("\\s+", "");
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
    public List<Invoice> search(String rsocial, String document) {
        return invoiceDao.findByrSocialContainingIgnoreCaseOrDocumentContainingIgnoreCase(rsocial, document);
    }
}
