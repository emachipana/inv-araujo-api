package com.inversionesaraujo.api.business.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Profit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitDTO {
    private Long id;
    private LocalDate date;
    private String month;
    private Double totalExpenses;
    private Double income;
    private Double profit;

    public static ProfitDTO toDTO(Profit profit) {
        return ProfitDTO
            .builder()
            .id(profit.getId())
            .date(profit.getDate())
            .month(profit.getMonth())
            .totalExpenses(profit.getTotalExpenses())
            .income(profit.getIncome())
            .profit(profit.getProfit())
            .build();
    }

    public static Profit toEntity(ProfitDTO profit) {
        return Profit
            .builder()
            .id(profit.getId())
            .date(profit.getDate())
            .month(profit.getMonth())
            .totalExpenses(profit.getTotalExpenses())
            .income(profit.getIncome())
            .profit(profit.getProfit())
            .build();
    }

    public static List<ProfitDTO> toListDTO(List<Profit> profits) {
        return profits
            .stream()
            .map(ProfitDTO::toDTO)
            .collect(Collectors.toList());
    }
}
