package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import com.inversionesaraujo.api.business.dto.payload.FileResponse;
import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.dto.request.InvoiceRequest;
import com.inversionesaraujo.api.business.service.I_Image;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.SortDirection;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private I_Image imageService;

    @GetMapping
    public Page<Invoice> getAll(
        @RequestParam(required = false) InvoiceType type,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(defaultValue = "DESC") SortDirection sort
    ) {
        return invoiceService.listAll(type, page, size, sort);
    }

    @GetMapping("search")
    public Page<Invoice> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return invoiceService.search(param, param, page);
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
            if(invoice.getPdfUrl() != null) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("El comprobante ya tienen un pdf generado")
                    .build(), HttpStatus.NOT_ACCEPTABLE);
            }

            if(invoice.getItems().size() == 0) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("Necesitas tener items")
                    .build(), HttpStatus.NOT_ACCEPTABLE);
            }

            FileResponse response = invoiceService.generateAndUploadPDF(invoice);
            invoice.setPdfUrl(response.getFileUrl());
            invoice.setPdfFirebaseId(response.getFileName());
            invoice.setIsGenerated(true);
            invoice.setSerie((invoice.getInvoiceType() == InvoiceType.BOLETA ? "B-" : "F-") + invoice.getId());
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

    @DeleteMapping("deletePDF/{id}")
    public ResponseEntity<MessageResponse> deletePDF(@PathVariable Integer id) {
        try {
            Invoice invoice = invoiceService.findById(id);
            if(invoice.getPdfUrl() == null) {
                return new ResponseEntity<>(MessageResponse
                    .builder()
                    .message("El comprobante not tiene un pdf generado")
                    .build(), HttpStatus.NOT_ACCEPTABLE);
            }

            imageService.deleteImage(invoice.getPdfFirebaseId());

            invoice.setPdfUrl(null);
            invoice.setPdfFirebaseId(null);
            invoice.setIsGenerated(false);
            invoice.setSerie(null);

            Invoice updatedInvoice = invoiceService.save(invoice);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El PDF se elimino correctamente")
                .data(updatedInvoice)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody InvoiceRequest request) {
        try {
            Invoice invoice = invoiceService.findById(id);
            invoice.setIssueDate(request.getIssueDate());
            invoice.setComment(request.getComment());
            invoice.setAddress(request.getAddress());
            Invoice updatedInvoice = invoiceService.save(invoice);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El comprobante se actualizo con exito")
                .data(updatedInvoice)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
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
