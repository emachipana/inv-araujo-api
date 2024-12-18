package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.OrderVariety;

public interface IOrderVariety {
    OrderVariety save(OrderVariety item);

    OrderVariety findById(Integer id);

    void delete(OrderVariety item);
}
