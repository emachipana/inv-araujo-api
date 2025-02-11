package com.inversionesaraujo.api.controller;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.InvoiceDTO;
import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.request.OrderRequest;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IOrder;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.business.service.I_Invoice;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private IOrder orderService;
    @Autowired
    private IClient clientService;
    @Autowired
    private I_Invoice invoiceService;
    @Autowired
    private IProfit profitService;

    @GetMapping
    public Page<OrderDTO> getAll(
        @RequestParam(required = false) Status status,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size,
        @RequestParam(defaultValue = "DESC") SortDirection sort,
        @RequestParam(required = false) Month month
    ) {
        return orderService.listAll(status, page, size, sort, month);
    }

    @GetMapping("search")
    public Page<OrderDTO> search(@RequestParam String param, @RequestParam(defaultValue = "0") Integer page) {
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
        OrderDTO order = orderService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se encontro con exito")
            .data(order)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OrderRequest request) {
        ClientDTO client = clientService.findById(request.getClientId());
        InvoiceDTO invoice = request.getInvoiceId() == null ? null : invoiceService.findById(request.getInvoiceId());
        LocalDate date = request.getDate() == null ? LocalDate.now() : request.getDate();
        LocalDate maxShipDate = date.plusDays(3);

        OrderDTO order = orderService.save(OrderDTO
            .builder()
            .client(client)
            .invoice(invoice)
            .department(request.getDepartment())
            .city(request.getCity())
            .date(date)
            .location(request.getLocation())
            .maxShipDate(maxShipDate)
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El pedido se creo con exito")
            .data(order)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody @Valid OrderRequest request) {
        OrderDTO order = orderService.findById(id);
        InvoiceDTO invoice = request.getInvoiceId() == null ? null : invoiceService.findById(request.getInvoiceId());
        LocalDate date = request.getDate();
        LocalDate maxShipDate = date.plusDays(3);
        
        order.setInvoice(invoice);
        order.setDepartment(request.getDepartment());
        order.setCity(request.getCity());
        order.setStatus(request.getStatus());
        order.setDate(date);
        order.setMaxShipDate(maxShipDate);
        order.setLocation(request.getLocation());

        OrderDTO orderUptaded = orderService.save(order);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se actualizo con exito")
            .data(orderUptaded)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        OrderDTO order = orderService.findById(id);
        ClientDTO client = order.getClient();
        LocalDate date = order.getDate();
        Month month = date.getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        Double income = profit.getIncome() - order.getTotal();
        profit.setIncome(income);
        profit.setProfit(income - profit.getTotalExpenses());
        profitService.save(profit);

        client.setConsumption(client.getConsumption() - order.getTotal());
        clientService.save(client);

        orderService.delete(order.getId());

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El pedido se elimino con exito")
            .build());
    }
}
