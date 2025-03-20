package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.OrderProductDTO;

public interface IOrderProduct {
    OrderProductDTO save(OrderProductDTO item);

    OrderProductDTO findById(Long id);

    List<OrderProductDTO> findByOrderId(Long orderId);

    void delete(Long id);
}
