package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.OrderVarietyDTO;

public interface IOrderVariety {
    OrderVarietyDTO save(OrderVarietyDTO item);

    OrderVarietyDTO findById(Long id);

    void delete(Long id);

    List<OrderVarietyDTO> findByVitroOrderId(Long orderId);
}
