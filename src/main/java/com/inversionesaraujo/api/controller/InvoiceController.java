package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.payload.FileResponse;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.InvoiceRequest;
import com.inversionesaraujo.api.business.service.I_Image;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.SortDirection;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private I_Image imageService;

    @GetMapping
    public Page<InvoiceDTO> getAll(
        @RequestParam(required = false) InvoiceType type,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(defaultValue = "DESC") SortDirection sort
    ) {
        return invoiceService.listAll(type, page, size, sort);
    }

    @GetMapping("search")
    public Page<InvoiceDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return invoiceService.search(param, param, page);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El comprobante se encontro con exito")
            .data(invoice)
            .build());
    }

    @PostMapping("generatePDF/{id}")
    public ResponseEntity<MessageResponse> generatePDF(@PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.findById(id);
        if(invoice.getPdfUrl() != null) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("El comprobante ya tienen un pdf generado")
                .build());
        }

        // TODO - Get items by query
        Integer items = 0;

        if(items == 0) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("Necesitas tener items")
                .build());
        }

        FileResponse response = invoiceService.generateAndUploadPDF(invoice);
        invoice.setPdfUrl(response.getFileUrl());
        invoice.setIsGenerated(true);
        invoice.setSerie((invoice.getInvoiceType() == InvoiceType.BOLETA ? "B-" : "F-") + invoice.getId());
        invoice.setPdfFirebaseId(response.getFileName());
        InvoiceDTO updatedInvoice = invoiceService.save(invoice);

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El PDF se genero correctamente")
            .data(updatedInvoice)
            .build());
    }

    @DeleteMapping("deletePDF/{id}")
    public ResponseEntity<MessageResponse> deletePDF(@PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.findById(id);
        if(invoice.getPdfUrl() == null) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("El comprobante not tiene un pdf generado")
                .build());
        }

        imageService.deleteImage(invoice.getPdfFirebaseId());
        invoice.setPdfUrl(null);
        invoice.setIsGenerated(false);
        invoice.setSerie(null);
        invoice.setPdfFirebaseId(null);

        InvoiceDTO updatedInvoice = invoiceService.save(invoice);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El PDF se elimino correctamente")
            .data(updatedInvoice)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid InvoiceRequest request) {
        InvoiceDTO newInvoice = invoiceService.save(InvoiceDTO
            .builder()
            .invoiceType(request.getInvoiceType())
            .document(request.getDocument())
            .documentType(request.getDocumentType())
            .rsocial(request.getRsocial())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El comprobante se creo con exito")
            .data(newInvoice)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid InvoiceRequest request) {
        InvoiceDTO invoice = invoiceService.findById(id);
        invoice.setInvoiceType(request.getInvoiceType());
        invoice.setDocumentType(request.getDocumentType());
        invoice.setDocument(request.getDocument());
        invoice.setRsocial(request.getRsocial());
        invoice.setIssueDate(request.getIssueDate());
        invoice.setComment(request.getComment());
        invoice.setAddress(request.getAddress());
        InvoiceDTO updatedInvoice = invoiceService.save(invoice);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El comprobante se actualizo con exito")
            .data(updatedInvoice)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.findById(id);
        String firebaseId = invoice.getPdfFirebaseId();
        invoiceService.delete(invoice.getId());
        if(firebaseId != null) imageService.deleteImage(firebaseId);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El comprobante se elimino con exito")
            .build());
    }
}
