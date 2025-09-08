package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.inversionesaraujo.api.model.Expense;
import com.inversionesaraujo.api.model.ExpenseType;
import com.inversionesaraujo.api.model.Profit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private Double subTotal;
    private Long profitId;
    private ExpenseType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ExpenseDTO toDTO(Expense expense) {
        return ExpenseDTO
            .builder()
            .id(expense.getId())
            .name(expense.getName())
            .price(expense.getPrice())
            .quantity(expense.getQuantity())
            .subTotal(expense.getSubTotal())
            .profitId(expense.getProfit().getId())
            .type(expense.getType())
            .createdAt(expense.getCreatedAt())
            .updatedAt(expense.getUpdatedAt())
            .build();
    }

    public static Expense toEntity(ExpenseDTO expense) {
        Profit profit = new Profit();
        profit.setId(expense.getProfitId());

        return Expense
            .builder()
            .id(expense.getId())
            .name(expense.getName())
            .price(expense.getPrice())
            .quantity(expense.getQuantity())
            .subTotal(expense.getSubTotal())
            .profit(profit)
            .type(expense.getType())
            .createdAt(expense.getCreatedAt())
            .updatedAt(expense.getUpdatedAt())
            .build();
    }

    public static List<ExpenseDTO> toDTOList(List<Expense> expenses) {
        return expenses.stream().map(ExpenseDTO::toDTO).toList();
    }
}
