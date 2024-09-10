package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.payload.FileResponse;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.service.I_Image;
import com.inversionesaraujo.api.service.I_Invoice;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private I_Image imageService;

    @GetMapping
    public List<Invoice> getAll() {
        return invoiceService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Invoice invoice = invoiceService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El comprobante se encontro con exito")
                .data(invoice)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("generatePDF/{id}")
    public ResponseEntity<MessageResponse> generatePDF(@PathVariable Integer id) {
        try {
            Invoice invoice = invoiceService.findById(id);
            if(invoice.getItems().size() == 0) {
                return new ResponseEntity<>(MessageResponse
                .builder()
                .message("Necesitas tener items")
                .build(), HttpStatus.NOT_ACCEPTABLE);
            }

            FileResponse response = invoiceService.generateAndUploadPDF(invoice);
            invoice.setPdfUrl(response.getFileUrl());
            invoice.setPdfFirebaseId(response.getFileName());
            Invoice updatedInvoice = invoiceService.save(invoice);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El PDF se genero correctamente")
                .data(updatedInvoice)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody Invoice invoice) {
        try {
            invoice.setTotal(0.0);
            invoice.setPdfUrl(null);
            invoice.setPdfFirebaseId(null);
            Invoice newInvoice = invoiceService.save(invoice);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El comprobante se creo con exito")
                .data(newInvoice)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        try {
            Invoice invoice = invoiceService.findById(id);
            String firebaseId = invoice.getPdfFirebaseId();
            invoiceService.delete(invoice);
            if(firebaseId != null) imageService.deleteImage(firebaseId);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El comprobante se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
