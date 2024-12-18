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
import com.inversionesaraujo.api.business.dto.request.ExpenseRequest;
import com.inversionesaraujo.api.business.service.IExpense;
import com.inversionesaraujo.api.business.service.IProfit;
import com.inversionesaraujo.api.model.Expense;
import com.inversionesaraujo.api.model.Profit;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    @Autowired
    private IExpense expenseService;
    @Autowired
    private IProfit profitService;

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Integer id) {
        try {
            Expense expense = expenseService.findById(id);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del gasto se encontro con exito")
                .data(expense)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody ExpenseRequest request) {
        try {
            Profit profit = profitService.findById(request.getProfitId());
            Double subTotal = request.getPrice() * request.getQuantity();
            Expense expense = expenseService.save(Expense
                .builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .subTotal(subTotal)
                .profit(profit)
                .build());

            updateProfit(profit, subTotal);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del gasto se creo con exito")
                .data(expense)
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }   
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody ExpenseRequest request, @PathVariable Integer id) {
        try {
            Profit profit = profitService.findById(request.getProfitId());
            Double subTotal = request.getPrice() * request.getQuantity();
            Expense expense = expenseService.findById(id);
            Double oldSubTotal = expense.getSubTotal();
            expense.setProfit(profit);
            expense.setSubTotal(subTotal);
            expense.setName(request.getName());
            expense.setPrice(request.getPrice());
            expense.setQuantity(request.getQuantity());
            Expense updatedExpense = expenseService.save(expense);

            profit.setTotalExpenses(profit.getTotalExpenses() - oldSubTotal);
            updateProfit(profit, subTotal);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del gasto se creo con exito")
                .data(updatedExpense)
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
            Expense expense = expenseService.findById(id);
            Profit profit = expense.getProfit();
            Double oldSubTotal = expense.getSubTotal();
            expenseService.delete(expense);
            Double updatedTotalExpenses = profit.getTotalExpenses() - oldSubTotal;
            profit.setTotalExpenses(updatedTotalExpenses);
            profit.setProfit(profit.getIncome() - updatedTotalExpenses);
            profitService.save(profit);

            return new ResponseEntity<>(MessageResponse
                .builder()
                .message("El registro del gasto se elimino con exito")
                .build(), HttpStatus.OK);
        }catch(Exception error) {
            return new ResponseEntity<>(MessageResponse
                .builder()
                .message(error.getMessage())
                .build(), HttpStatus.NOT_FOUND);
        }
    }

    private void updateProfit(Profit profit, Double subTotal) {
        Double updatedTotalExpenses = profit.getTotalExpenses() + subTotal;
        Double updatedProfit = profit.getIncome() - updatedTotalExpenses; 
        profit.setTotalExpenses(updatedTotalExpenses);
        profit.setProfit(updatedProfit);
        profitService.save(profit);
    }
}
