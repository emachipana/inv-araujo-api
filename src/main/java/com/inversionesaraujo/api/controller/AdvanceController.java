package com.inversionesaraujo.api.controller;

import java.time.Month;

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
import com.inversionesaraujo.api.business.dto.request.AdvanceRequest;
import com.inversionesaraujo.api.business.service.IAdvance;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import com.inversionesaraujo.api.model.Advance;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.Profit;
import com.inversionesaraujo.api.model.VitroOrder;

@RestController
@RequestMapping("/api/v1/advances")
public class AdvanceController {
    @Autowired
    private IAdvance advanceService;
    @Autowired
    private IVitroOrder orderService;
    @Autowired
    private IProfit profitService;
    @Autowired
    private IClient clientService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Advance advance = advanceService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El adelanto se encontro con exito")
                .data(advance)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody AdvanceRequest request) {
        try {
            VitroOrder order = orderService.findById(request.getVitroOrderId());
            Double amount = request.getAmount();
            Advance advance = advanceService.save(Advance
                .builder()
                .vitroOrder(order)
                .amount(amount)
                .date(request.getDate())
                .build());

            Double totalAdvance = order.getTotalAdvance() + amount;
            Client client = order.getClient();
            order.setTotalAdvance(totalAdvance);
            order.setPending(order.getTotal() - totalAdvance);
            orderService.save(order);

            client.setConsumption(client.getConsumption() + amount);
            clientService.save(client);

            Month month = request.getDate().getMonth();
            Profit profit = profitService.findByMonth(month.toString());
            if(profit == null) {
                profitService.save(Profit.builder()
                    .date(request.getDate())
                    .income(amount)
                    .profit(amount)
                    .month(month.toString())
                    .build()
                );
            }else {
                Double income = profit.getIncome() + amount;
                profit.setIncome(income);
                profit.setProfit(income - profit.getTotalExpenses());
                profitService.save(profit);
            }

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El adelanto se creo con exito")
                .data(advance)
                .build(), HttpStatus.CREATED);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody AdvanceRequest request) {
        try {
            Advance advance = advanceService.findById(id);
            Double amount = request.getAmount();
            Double oldAmount = advance.getAmount();
            advance.setAmount(amount);
            advance.setDate(request.getDate());
            VitroOrder order = advance.getVitroOrder();
            Advance advanceUpdated = advanceService.save(advance);

            Double totalAdvance = (order.getTotalAdvance() - oldAmount) + amount;
            Client client = order.getClient();
            order.setTotalAdvance(totalAdvance);
            order.setPending(order.getTotal() - totalAdvance);
            orderService.save(order);

            client.setConsumption((client.getConsumption() - oldAmount) + amount);
            clientService.save(client);

            Month month = request.getDate().getMonth();
            Profit profit = profitService.findByMonth(month.toString());
            Double income = (profit.getIncome() - oldAmount) + amount;
            profit.setIncome(income);
            profit.setProfit(income - profit.getTotalExpenses());
            profitService.save(profit);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El adelanto se actualizo con exito")
                .data(advanceUpdated)
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
            Advance advance = advanceService.findById(id);
            Double oldAmount = advance.getAmount();
            VitroOrder order = advance.getVitroOrder();
            advanceService.delete(advance);

            Double totalAdvance = order.getTotalAdvance() - oldAmount;
            Client client = order.getClient();
            order.setTotalAdvance(totalAdvance);
            order.setPending(order.getTotal() - totalAdvance);
            orderService.save(order);

            client.setConsumption(client.getConsumption() - oldAmount);

            Month month = advance.getDate().getMonth();
            Profit profit = profitService.findByMonth(month.toString());
            Double income = (profit.getIncome() - oldAmount);
            profit.setIncome(income);
            profit.setProfit(income - profit.getTotalExpenses());
            profitService.save(profit);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El adelanto se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }
}
