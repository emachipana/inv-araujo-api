package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.dto.request.InvoiceItemRequest;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.business.service.I_InvoiceItem;
import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.InvoiceItem;

@RestController
@RequestMapping("/api/v1/invoiceItems")
public class InvoiceItemController {
    @Autowired
    private I_InvoiceItem invoiceItemService;
    @Autowired
    private I_Invoice invoiceService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            InvoiceItem item = invoiceItemService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("Se encontro el item del comprobante con exito")
                .data(item)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody InvoiceItemRequest request) {
        try {
            Invoice invoice = invoiceService.findById(request.getInvoiceId());
            Double subTotal = request.getPrice() * request.getQuantity();

            InvoiceItem newItem = invoiceItemService.save(InvoiceItem
                .builder()
                .invoice(invoice)
                .subTotal(subTotal)
                .name(request.getName())
                .itemCode(request.getItemCode())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .isIgvApply(request.isIgvApply())
                .build());

            invoice.setTotal(invoice.getTotal() + subTotal);
            invoiceService.save(invoice);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item del comprobante se creo con exito")
                .data(newItem)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody InvoiceItemRequest request) {
        try {
            InvoiceItem item = invoiceItemService.findById(id);
            Invoice invoice = item.getInvoice();
            Double subTotal = request.getPrice() * request.getQuantity();
            Double oldSubTotal = item.getSubTotal();

            item.setIgvApply(request.isIgvApply());
            item.setInvoice(invoice);
            item.setQuantity(request.getQuantity());
            item.setPrice(request.getPrice());
            item.setSubTotal(subTotal);
            item.setName(request.getName());
            item.setItemCode(request.getItemCode());
            InvoiceItem itemUpdated = invoiceItemService.save(item);

            invoice.setTotal((invoice.getTotal() - oldSubTotal) + subTotal);
            invoiceService.save(invoice);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item se actualizo con exito")
                .data(itemUpdated)
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
            InvoiceItem item = invoiceItemService.findById(id);
            Invoice invoice = item.getInvoice();
            Double oldSubTotal = item.getSubTotal();
            invoiceItemService.delete(item);

            invoice.setTotal(invoice.getTotal() - oldSubTotal);
            invoiceService.save(invoice);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El item se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
