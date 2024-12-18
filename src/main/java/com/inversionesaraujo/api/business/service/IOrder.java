package com.inversionesaraujo.api.business.service;

import java.time.Month;
import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.payload.OrderDataResponse;
import com.inversionesaraujo.api.model.Order;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

public interface IOrder {
    Page<Order> listAll(
        Status status, Integer page, Integer size, SortDirection direction,
        Month month
    );

    OrderDataResponse getData();

    Order save(Order order);

    Order findById(Integer id);

    void delete(Order order);

    List<Order> search(String department, String city, String rsocial);
}
