package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.model.Profit;
import com.inversionesaraujo.api.model.SortDirection;

public interface IProfit {
    List<Profit> listAll(SortDirection sort);

    Profit save(Profit profit);

    Profit findById(Integer id);

    Profit findByMonth(String month);

    void delete(Profit profit);
}
