package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Order;

public interface IOrder {
    List<Order> listAll();

    Order save(Order order);

    Order findById(Integer id);

    void delete(Order order);
}
