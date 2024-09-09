package com.inversionesaraujo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.InvoiceItem;
import com.inversionesaraujo.api.model.entity.Product;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.InvoiceItemRequest;
import com.inversionesaraujo.api.service.IProduct;
import com.inversionesaraujo.api.service.I_Invoice;
import com.inversionesaraujo.api.service.I_InvoiceItem;

@RestController
@RequestMapping("/api/v1/invoiceItems")
public class InvoiceItemController {
    @Autowired
    private I_InvoiceItem invoiceItemService;
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private IProduct productService;

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
            Product product = productService.findById(request.getProductId());
            Double price = product.getPrice();
            if(product.getDiscount() != null) price = product.getDiscount().getPrice();
            Double subTotal = price * request.getQuantity();

            InvoiceItem newItem = invoiceItemService.save(InvoiceItem
                .builder()
                .invoice(invoice)
                .product(product)
                .subTotal(subTotal)
                .price(price)
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
}
