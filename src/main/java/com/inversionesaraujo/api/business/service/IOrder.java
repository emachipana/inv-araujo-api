package com.inversionesaraujo.api.business.service;

import java.time.Month;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.OrderDTO;
import com.inversionesaraujo.api.business.payload.OrderDataResponse;
import com.inversionesaraujo.api.business.payload.TotalDeliverResponse;
import com.inversionesaraujo.api.model.OrderLocation;
import com.inversionesaraujo.api.model.ShippingType;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.model.Status;

public interface IOrder {
    Page<OrderDTO> listAll(
        Status status, Integer page, Integer size, SortDirection direction,
        Month month, SortBy sort, ShippingType shipType, Long warehouseId,
        Long employeeId, OrderLocation location
    );

    OrderDataResponse getData();

    OrderDTO save(OrderDTO order);

    OrderDTO findById(Long id);

    void delete(Long id);

    Page<OrderDTO> search(String department, String city, String rsocial, Integer page);

    TotalDeliverResponse totalDeliver();

    void sendNewOrder(OrderDTO order, Double total);
}
