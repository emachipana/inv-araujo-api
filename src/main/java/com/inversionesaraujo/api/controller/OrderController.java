package com.inversionesaraujo.api.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.model.entity.Client;
import com.inversionesaraujo.api.model.entity.Invoice;
import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.Profit;
import com.inversionesaraujo.api.model.payload.MessageResponse;
import com.inversionesaraujo.api.model.request.OrderRequest;
import com.inversionesaraujo.api.service.IClient;
import com.inversionesaraujo.api.service.IOrder;
import com.inversionesaraujo.api.service.IProfit;
import com.inversionesaraujo.api.service.I_Invoice;

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
    public List<Order> getAll() {
        return orderService.listAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Order order = orderService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido se encontro con exito")
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
    public ResponseEntity<MessageResponse> create(@RequestBody OrderRequest request) {
        try {
            Client client = clientService.findById(request.getClientId());
            Invoice invoice = request.getInvoiceId() == null ? null : invoiceService.findById(request.getInvoiceId());
            LocalDate date = request.getDate() == null ? LocalDate.now() : request.getDate();
            LocalDate maxShipDate = date.plusDays(3);

            Order order = orderService.save(Order
                .builder()
                .client(client)
                .invoice(invoice)
                .department(request.getDepartment())
                .city(request.getCity())
                .date(date)
                .maxShipDate(maxShipDate)
                .build());

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido se creo con exito")
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
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody OrderRequest request) {
        try {
            Order order = orderService.findById(id);
            Invoice invoice = request.getInvoiceId() == null ? null : invoiceService.findById(request.getInvoiceId());
            LocalDate date = request.getDate();
            LocalDate maxShipDate = date.plusDays(3);
            
            order.setInvoice(invoice);
            order.setDepartment(request.getDepartment());
            order.setCity(request.getCity());
            order.setStatus(request.getStatus());
            order.setDate(date);
            order.setMaxShipDate(maxShipDate);

            Order orderUptaded = orderService.save(order);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido se actualizo con exito")
                .data(orderUptaded)
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
            Order order = orderService.findById(id);
            Client client = order.getClient();
            LocalDate date = order.getDate();
            Month month = date.getMonth();
            Profit profit = profitService.findByMonth(month.toString());
            Double income = profit.getIncome() - order.getTotal();
            profit.setIncome(income);
            profit.setProfit(income - profit.getTotalExpenses());
            profitService.save(profit);

            client.setConsumption(client.getConsumption() - order.getTotal());
            clientService.save(client);

            orderService.delete(order);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El pedido se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);  
        }
    }
}
