package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.inversionesaraujo.api.business.dto.InvoiceItemDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.InvoiceItemRequest;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.business.service.I_InvoiceItem;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/invoiceItems")
public class InvoiceItemController {
    @Autowired
    private I_InvoiceItem invoiceItemService;
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping("/invoice/{invoiceId}")
    public List<InvoiceItemDTO> findByInvoiceId(@PathVariable Long invoiceId) {
        return invoiceItemService.findByInvoiceId(invoiceId);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        InvoiceItemDTO item = invoiceItemService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Se encontro el item del comprobante con exito")
            .data(item)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid InvoiceItemRequest request) {
        InvoiceDTO invoice = invoiceService.findById(request.getInvoiceId());
        Double subTotal = request.getPrice() * request.getQuantity();

        InvoiceItemDTO newItem = invoiceItemService.save(InvoiceItemDTO
            .builder()
            .invoiceId(invoice.getId())
            .subTotal(subTotal)
            .name(request.getName())
            .price(request.getPrice())
            .quantity(request.getQuantity())
            .isIgvApply(request.getIsIgvApply())
            .unit(request.getUnit())
            .build());

        invoice.setTotal(invoice.getTotal() + subTotal);
        invoiceService.save(invoice);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Agrego un item al comprobante")
                .redirectTo("/comprobantes/" + invoice.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El item del comprobante se creo con exito")
            .data(newItem)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid InvoiceItemRequest request) {
        InvoiceItemDTO item = invoiceItemService.findById(id);
        InvoiceDTO invoice = invoiceService.findById(request.getInvoiceId());
        Double subTotal = request.getPrice() * request.getQuantity();
        Double oldSubTotal = item.getSubTotal();

        item.setIsIgvApply(request.getIsIgvApply());
        item.setQuantity(request.getQuantity());
        item.setPrice(request.getPrice());
        item.setSubTotal(subTotal);
        item.setName(request.getName());
        InvoiceItemDTO itemUpdated = invoiceItemService.save(item);

        invoice.setTotal((invoice.getTotal() - oldSubTotal) + subTotal);
        invoiceService.save(invoice);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un item del comprobante")
                .redirectTo("/comprobantes/" + invoice.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item se actualizo con exito")
            .data(itemUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id, @RequestParam(required = false) Long employeeId) {
        InvoiceItemDTO item = invoiceItemService.findById(id);
        InvoiceDTO invoice = invoiceService.findById(item.getInvoiceId());
        Double oldSubTotal = item.getSubTotal();
        invoiceItemService.delete(id);

        invoice.setTotal(invoice.getTotal() - oldSubTotal);
        invoiceService.save(invoice);

        if(employeeId != null && employeeId != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(employeeId)
                .operation("Elimino un item del comprobante")
                .redirectTo("/comprobantes/" + invoice.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El item se elimino con exito")
            .build());
    }
}
