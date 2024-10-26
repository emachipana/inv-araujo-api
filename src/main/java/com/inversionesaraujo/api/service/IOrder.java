package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.Status;

public interface IOrder {
    List<Order> listAll();

    Order save(Order order);

    Order findById(Integer id);

    void delete(Order order);

    List<Order> search(String department, String city, String rsocial);

    List<Order> findByStatus(Status status);
}
