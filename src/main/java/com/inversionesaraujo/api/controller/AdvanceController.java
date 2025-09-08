package com.inversionesaraujo.api.controller;

import java.time.Month;
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
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.AdvanceDTO;
import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.dto.VitroOrderDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.AdvanceRequest;
import com.inversionesaraujo.api.business.service.IAdvance;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.business.service.IVitroOrder;
import jakarta.validation.Valid;

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
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        AdvanceDTO advance = advanceService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El adelanto se encontro con exito")
            .data(advance)
            .build());
    }

    @GetMapping("vitroOrder/{id}")
    public List<AdvanceDTO> getAllByVitroOrder(@PathVariable Long id) {
        return advanceService.findByVitroOrder(id);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid AdvanceRequest request) {
        VitroOrderDTO order = orderService.findById(request.getVitroOrderId());
        Double amount = request.getAmount();

        AdvanceDTO advance = advanceService.save(AdvanceDTO
            .builder()
            .vitroOrderId(order.getId())
            .amount(amount)
            .paymentType(request.getPaymentType())
            .build());

        Double totalAdvance = order.getTotalAdvance() + amount;
        ClientDTO client = order.getClient();
        order.setTotalAdvance(totalAdvance);
        order.setPending(order.getTotal() - totalAdvance);
        orderService.save(order);

        client.setConsumption(client.getConsumption() + amount);
        clientService.save(client);

        Month month = advance.getCreatedAt().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        if(profit == null) {
            profitService.save(ProfitDTO.builder()
                .date(advance.getCreatedAt().toLocalDate())
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

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Creo un adelanto de un pedido invitro")
                .redirectTo("/invitro/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El adelanto se creo con exito")
            .data(advance)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Long id, @RequestBody AdvanceRequest request) {
        AdvanceDTO advance = advanceService.findById(id);
        Double amount = request.getAmount();
        Double oldAmount = advance.getAmount();
        advance.setAmount(amount);
        VitroOrderDTO order = orderService.findById(advance.getVitroOrderId());
        AdvanceDTO advanceUpdated = advanceService.save(advance);

        Double totalAdvance = (order.getTotalAdvance() - oldAmount) + amount;
        ClientDTO client = order.getClient();
        order.setTotalAdvance(totalAdvance);
        order.setPending(order.getTotal() - totalAdvance);
        orderService.save(order);

        client.setConsumption((client.getConsumption() - oldAmount) + amount);
        clientService.save(client);

        Month month = advance.getUpdatedAt().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        Double income = (profit.getIncome() - oldAmount) + amount;
        profit.setIncome(income);
        profit.setProfit(income - profit.getTotalExpenses());
        profitService.save(profit);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo un adelanto de un pedido invitro")
                .redirectTo("/invitro/" + order.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El adelanto se actualizo con exito")
            .data(advanceUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        AdvanceDTO advance = advanceService.findById(id);
        Double oldAmount = advance.getAmount();
        VitroOrderDTO order = orderService.findById(advance.getVitroOrderId());
        advanceService.delete(advance.getId());

        Double totalAdvance = order.getTotalAdvance() - oldAmount;
        ClientDTO client = order.getClient();
        order.setTotalAdvance(totalAdvance);
        order.setPending(order.getTotal() - totalAdvance);
        orderService.save(order);

        client.setConsumption(client.getConsumption() - oldAmount);

        Month month = advance.getUpdatedAt().getMonth();
        ProfitDTO profit = profitService.findByMonth(month.toString());
        Double income = (profit.getIncome() - oldAmount);
        profit.setIncome(income);
        profit.setProfit(income - profit.getTotalExpenses());
        profitService.save(profit);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El adelanto se elimino con exito")
            .build());
    }
}
