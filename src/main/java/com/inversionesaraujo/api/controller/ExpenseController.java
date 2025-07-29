package com.inversionesaraujo.api.controller;

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

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.dto.ExpenseDTO;
import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.ExpenseRequest;
import com.inversionesaraujo.api.business.service.IExpense;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.business.service.IProfit;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    @Autowired
    private IExpense expenseService;
    @Autowired
    private IProfit profitService;
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        ExpenseDTO expense = expenseService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El registro del gasto se encontro con exito")
            .data(expense)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid ExpenseRequest request) {
        ProfitDTO profit = profitService.findById(request.getProfitId());
        Double subTotal = request.getPrice() * request.getQuantity();

        ExpenseDTO expense = expenseService.save(ExpenseDTO
            .builder()
            .name(request.getName())
            .price(request.getPrice())
            .quantity(request.getQuantity())
            .subTotal(subTotal)
            .profitId(profit.getId())
            .type(request.getType())
            .build());

        updateProfit(profit, subTotal);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Registro un gasto")
                .redirectTo("/gastos/" + expense.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El registro del gasto se creo con exito")
            .data(expense)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid ExpenseRequest request, @PathVariable Long id) {
        Double subTotal = request.getPrice() * request.getQuantity();
        ExpenseDTO expense = expenseService.findById(id);
        Double oldSubTotal = expense.getSubTotal();
        ProfitDTO profit = profitService.findById(expense.getProfitId());

        expense.setSubTotal(subTotal);
        expense.setName(request.getName());
        expense.setPrice(request.getPrice());
        expense.setQuantity(request.getQuantity());
        expense.setType(request.getType());
        ExpenseDTO updatedExpense = expenseService.save(expense);

        profit.setTotalExpenses(profit.getTotalExpenses() - oldSubTotal);
        updateProfit(profit, subTotal);

        if(request.getEmployeeId() != null && request.getEmployeeId() != 1L) {
            EmployeeOperationDTO employeeOperation = EmployeeOperationDTO
                .builder()
                .employeeId(request.getEmployeeId())
                .operation("Actualizo el registro de un gasto")
                .redirectTo("/gastos/" + expense.getId())
                .build();

            employeeOperationService.save(employeeOperation);
        }

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El registro del gasto se actualizo con exito")
            .data(updatedExpense)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        ExpenseDTO expense = expenseService.findById(id);
        ProfitDTO profit = profitService.findById(expense.getProfitId());
        Double oldSubTotal = expense.getSubTotal();
        expenseService.delete(expense.getId());

        Double updatedTotalExpenses = profit.getTotalExpenses() - oldSubTotal;
        profit.setTotalExpenses(updatedTotalExpenses);
        profit.setProfit(profit.getIncome() - updatedTotalExpenses);
        profitService.save(profit);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El registro del gasto se elimino con exito")
            .build());
    }

    private void updateProfit(ProfitDTO profit, Double subTotal) {
        Double updatedTotalExpenses = profit.getTotalExpenses() + subTotal;
        Double updatedProfit = profit.getIncome() - updatedTotalExpenses; 
        profit.setTotalExpenses(updatedTotalExpenses);
        profit.setProfit(updatedProfit);
        profitService.save(profit);
    }
}
