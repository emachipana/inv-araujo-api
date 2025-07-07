package com.inversionesaraujo.api.controller;

import java.time.LocalDateTime;

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

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.payload.ApiSunatResponse;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.InvoiceRequest;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.business.service.I_InvoiceItem;
import com.inversionesaraujo.api.model.InvoiceType;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.SortBy;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private I_InvoiceItem itemService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping
    public Page<InvoiceDTO> getAll(
        @RequestParam(required = false) InvoiceType type,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(defaultValue = "DESC") SortDirection direction,
        @RequestParam(required = false) SortBy sortby
    ) {
        return invoiceService.listAll(type, page, size, direction, sortby);
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
    public ResponseEntity<MessageResponse> generatePDF(@PathVariable Long id, @RequestBody Long employeeId) {
        InvoiceDTO invoice = invoiceService.findById(id);
        if(invoice.getPdfUrl() != null) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("El comprobante ya tienen un pdf generado")
                .build());
        }

        Integer items = itemService.countByInvoiceId(id);

        if(items == 0) {
            return ResponseEntity.status(406).body(MessageResponse
                .builder()
                .message("Necesitas tener items")
                .build());
        }

        ApiSunatResponse sunatResponse = invoiceService.sendInvoiceToSunat(invoice);

        invoice.setIsSended(true);
        invoice.setPdfUrl(sunatResponse.getEnlace_del_pdf());
        invoiceService.save(invoice);

        if(employeeId != null && employeeId != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(employeeId)
                .operation("Emitio el comprobante")
                .redirectTo("/comprobantes/" + invoice.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El comprobante se emitio correctamente")
            .data(sunatResponse)
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
            .serie(request.getInvoiceType() == InvoiceType.BOLETA ? "B001" : "F001")
            .address(request.getAddress())
            .issueDate(request.getIssueDate() != null ? request.getIssueDate() : LocalDateTime.now())
            .isSended(false)
            .total(0.0)
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un comprobante")
                .redirectTo("/comprobantes/" + newInvoice.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

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
        invoice.setAddress(request.getAddress());
        InvoiceDTO updatedInvoice = invoiceService.save(invoice);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();
            
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un comprobante")
                .redirectTo("/comprobantes/" + updatedInvoice.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El comprobante se actualizo con exito")
            .data(updatedInvoice)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        invoiceService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El comprobante se elimino con exito")
            .build());
    }
}
