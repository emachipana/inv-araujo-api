package com.inversionesaraujo.api.controller;

import java.time.LocalDate;
import java.time.Month;

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

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.request.VitroOrderRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

import jakarta.validation.Valid;

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
    public Page<VitroOrderDTO> getAll(
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
    public Page<VitroOrderDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
        return orderService.search(param, param, param, page);
    }

    @GetMapping("data")
    public ResponseEntity<MessageResponse> getData() {
        OrderDataResponse response = orderService.getData();

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("Los datos se obtuvieron con éxito")
            .data(response)
            .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        VitroOrderDTO order = orderService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido invitro se creo con exito")
            .data(order)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid VitroOrderRequest request) {
        ClientDTO client = clientService.findById(request.getClientId());
        LocalDate initDate = request.getInitDate() != null ? request.getInitDate() : LocalDate.now();

        VitroOrderDTO order = orderService.save(VitroOrderDTO
            .builder()
            .client(client)
            .department(request.getDepartment())
            .city(request.getCity())
            .initDate(initDate)
            .finishDate(request.getFinishDate())
            .location(request.getLocation())
            .shippingType(request.getShippingType())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El pedido invitro se creo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid VitroOrderRequest request) {
        VitroOrderDTO order = orderService.findById(id);
        InvoiceDTO invoice = request.getInvoiceId() == null ? null : invoiceService.findById(request.getInvoiceId());

        order.setDepartment(request.getDepartment());
        order.setCity(request.getCity());
        order.setInitDate(request.getInitDate());
        order.setFinishDate(request.getFinishDate());
        order.setStatus(request.getStatus());
        order.setInvoice(invoice);
        order.setShippingType(request.getShippingType());
        order.setPending(order.getTotal() - order.getTotalAdvance());
        order.setLocation(request.getLocation());
        
        VitroOrderDTO orderUpdated = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido invitro se actualizo con exito")
            .data(orderUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        VitroOrderDTO order = orderService.findById(id);
        ClientDTO client = order.getClient();
        Double totalAdvance = order.getTotalAdvance();
        orderService.delete(id);

        client.setConsumption(client.getConsumption() - totalAdvance);
        clientService.save(client);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido invitro se elimino con exito")
            .build());
    }
}
