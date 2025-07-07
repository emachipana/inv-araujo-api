package com.inversionesaraujo.api.config.db.seeders;

import com.inversionesaraujo.api.business.dto.ProfitDTO;
import com.inversionesaraujo.api.business.service.IProfit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MonthlyProfitSeeder {
    @Autowired
    private IProfit profitService;

    public void seed() {
        LocalDate now = LocalDate.now();
        String month = now.getMonth().toString();

        if (profitService.findByMonth(month) != null) {
            System.out.println("Registro de ganancias ya existe para " + month);
            return;
        }

        profitService.save(ProfitDTO.builder()
            .date(now)
            .month(month)
            .income(0.0)
            .profit(0.0)
            .totalExpenses(0.0)
            .build());

        System.out.println("Registro de ganancias creado para " + month);
    }
}
