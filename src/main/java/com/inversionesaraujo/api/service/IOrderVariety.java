package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.OrderVariety;

public interface IOrderVariety {
    OrderVariety save(OrderVariety item);

    OrderVariety findById(Integer id);

    void delete(OrderVariety item);

    boolean ifExists(Integer id);
}
