package com.inversionesaraujo.api.business.service;

import java.time.Month;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

public interface IOrder {
    Page<OrderDTO> listAll(
        Status status, Integer page, Integer size, SortDirection direction,
        Month month
    );

    OrderDataResponse getData();

    OrderDTO save(OrderDTO order);

    OrderDTO findById(Long id);

    void delete(Long id);

    Page<OrderDTO> search(String department, String city, String rsocial, Integer page);
}
