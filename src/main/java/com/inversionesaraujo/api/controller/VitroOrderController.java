package com.inversionesaraujo.api.controller;

import java.time.Month;

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

import com.inversionesaraujo.api.business.dto.payload.MessageResponse;
import com.inversionesaraujo.api.business.dto.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.dto.request.VitroOrderRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.Invoice;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;
import com.inversionesaraujo.api.model.VitroOrder;

@RestController
@RequestMapping("/api/v1/vitroOrders")
public class VitroOrderController {
    @Autowired
    private IVitroOrder orderService;
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private IClient clientService;

    @GetMapping
    public Page<VitroOrder> getAll(
        @RequestParam(required = false) Integer tuberId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(defaultValue = "DESC") SortDirection sort,
        @RequestParam(required = false) Month month,
        @RequestParam(required = false) Status status
    ) {
        return orderService.listAll(tuberId, page, size, sort, month, status);
    }

    
    @GetMapping("search")
    public Page<VitroOrder> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return orderService.search(param, param, param, page);
    }

    @GetMapping("data")
    public ResponseEntity<MessageResponse> getData() {
        try {
            OrderDataResponse response = orderService.getData();

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("Los datos se obtuvieron con éxito")
                .data(response)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);  
        }
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
            Client client = clientService.findById(request.getClientId());

            VitroOrder order = orderService.save(VitroOrder
                .builder()
                .client(client)
                .department(request.getDepartment())
                .city(request.getCity())
                .initDate(request.getInitDate())
                .finishDate(request.getFinishDate())
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

            order.setDepartment(request.getDepartment());
            order.setCity(request.getCity());
            order.setInitDate(request.getInitDate());
            order.setFinishDate(request.getFinishDate());
            order.setStatus(request.getStatus());
            order.setInvoice(invoice);
            order.setPending(order.getTotal() - order.getTotalAdvance());
            
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
            Client client = order.getClient();
            Double totalAdvance = order.getTotalAdvance();
            orderService.delete(order);

            client.setConsumption(client.getConsumption() - totalAdvance);
            clientService.save(client);

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
