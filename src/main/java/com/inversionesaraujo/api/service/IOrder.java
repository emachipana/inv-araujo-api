package com.inversionesaraujo.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.entity.Order;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.model.entity.Status;

public interface IOrder {
    Page<Order> listAll(
        Status status, Integer page, Integer size, SortDirection direction
    );

    Order save(Order order);

    Order findById(Integer id);

    void delete(Order order);

    List<Order> search(String department, String city, String rsocial);
}
