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
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.InvoiceClientDetailDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.ClientRequest;
import com.inversionesaraujo.api.business.request.InvoiceClientDetailRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.I_InvoiceClientDetail;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.SortBy;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @Autowired
    private IClient clientService;
    @Autowired
    private I_InvoiceClientDetail invoiceClientDetailService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping
    public Page<ClientDTO> getAll(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(required = false) SortDirection direction,
        @RequestParam(required = false) SortBy sortby
    ) {
        return clientService.filterClients(page, size, direction, sortby);
    }

    @GetMapping("/search")
    public Page<ClientDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return clientService.search(param, param, page);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El client se encontro con exito")
            .data(client)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid ClientRequest request) {
        ClientDTO clientToSave = clientService.save(ClientDTO
            .builder()
            .phone(request.getPhone())
            .document(request.getDocument())
            .documentType(request.getDocumentType())
            .rsocial(request.getRsocial())
            .createdBy(request.getCreatedBy())
            .email(request.getEmail())
            .userId(null)
            .consumption(0.0)
            .build());

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();

            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un cliente")
                .redirectTo("/clientes/" + clientToSave.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }
        
        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El client se creo con exito")
            .data(clientToSave)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid ClientRequest request, @PathVariable Long id) {
        ClientDTO client = clientService.findById(id);
        client.setPhone(request.getPhone());
        client.setDocument(request.getDocument());
        client.setRsocial(request.getRsocial());
        ClientDTO clientUpdated = clientService.save(client);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            LocalDateTime now = LocalDateTime.now();

            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un cliente")
                .redirectTo("/clientes/" + clientUpdated.getId())
                .createdAt(now)
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El cliente se actualizo con exito")
            .data(clientUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        clientService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El cliente se elimino con exito")
            .build());
    }

    @PostMapping("/{id}/invoiceDetails")
    public ResponseEntity<MessageResponse> createInvoiceDetail(@PathVariable Long id, @RequestBody @Valid InvoiceClientDetailRequest request) {
        InvoiceClientDetailDTO detailToSave = invoiceClientDetailService.save(InvoiceClientDetailDTO
            .builder()
            .document(request.getDocument())
            .documentType(request.getDocumentType())
            .rsocial(request.getRsocial())
            .address(request.getAddress())
            .invoicePreference(request.getInvoicePreference())
            .clientId(id)
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El detalle de facturación del cliente se creo con exito")
            .data(detailToSave)
            .build());
    }

    @PutMapping("/{id}/invoiceDetails/{detailId}")
    public ResponseEntity<MessageResponse> updateInvoiceDetail(@PathVariable Long id, @PathVariable Long detailId, @RequestBody @Valid InvoiceClientDetailRequest request) {
        InvoiceClientDetailDTO detail = invoiceClientDetailService.findById(detailId);
        detail.setDocument(request.getDocument());
        detail.setDocumentType(request.getDocumentType());
        detail.setRsocial(request.getRsocial());
        detail.setAddress(request.getAddress());
        detail.setInvoicePreference(request.getInvoicePreference());
        InvoiceClientDetailDTO detailUpdated = invoiceClientDetailService.save(detail);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El detalle de facturación del cliente se actualizo con exito")
            .data(detailUpdated)
            .build());
    }
}
