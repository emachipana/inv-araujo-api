package com.inversionesaraujo.api.controller;

import java.util.List;

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

import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.VitroOrder;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.VitroOrderRequest;
import com.inversionesaraujo.api.service.IVitroOrder;
import com.inversionesaraujo.api.service.I_Invoice;

@RestController
@RequestMapping("/api/v1/vitroOrders")
public class VitroOrderController {
    @Autowired
    private IVitroOrder orderService;
    @Autowired
    private I_Invoice invoiceService;

    @GetMapping
    public List<VitroOrder> getAll() {
        return orderService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            VitroOrder order = orderService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido invitro se creo con exito")
                .data(order)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody VitroOrderRequest request) {
        try {
            Invoice invoice = request.getInvoiceId() == null ? null : invoiceService.findById(request.getInvoiceId());
            VitroOrder order = orderService.save(VitroOrder
                .builder()
                .documentType(request.getDocType())
                .document(request.getDocument())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .destination(request.getDestination())
                .advance(request.getAdvance())
                .initDate(request.getInitDate())
                .finishDate(request.getFinishDate())
                .phone(request.getPhone())
                .invoice(invoice)
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido invitro se creo con exito")
                .data(order)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody VitroOrderRequest request) {
        try {
            VitroOrder order = orderService.findById(id);
            Invoice invoice = request.getInvoiceId() == null ? null : invoiceService.findById(request.getInvoiceId());
            order.setDocumentType(request.getDocType());
            order.setDocument(request.getDocument());
            order.setFirstName(request.getFirstName());
            order.setLastName(request.getLastName());
            order.setDestination(request.getDestination());
            order.setAdvance(request.getAdvance());
            order.setInitDate(request.getInitDate());
            order.setFinishDate(request.getFinishDate());
            order.setPhone(request.getPhone());
            order.setStatus(request.getStatus());
            order.setInvoice(invoice);
            order.setPending(order.getTotal() - request.getAdvance());
            
            VitroOrder orderUpdated = orderService.save(order);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido invitro se actualizo con exito")
                .data(orderUpdated)
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
            VitroOrder order = orderService.findById(id);
            orderService.delete(order);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido invitro se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
