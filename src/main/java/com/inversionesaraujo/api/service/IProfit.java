package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Profit;

public interface IProfit {
    List<Profit> listAll();

    Profit save(Profit profit);

    Profit findById(Integer id);

    Profit findByMonth(String month);

    void delete(Profit profit);
}
