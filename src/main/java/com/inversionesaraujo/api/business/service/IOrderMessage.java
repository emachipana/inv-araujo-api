package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.OrderMessageDTO;

public interface IOrderMessage {
    OrderMessageDTO save(OrderMessageDTO orderMessageDTO);

    void alertNewMessage(OrderMessageDTO orderMessageDTO);

    List<OrderMessageDTO> findByOrderId(Long orderId);
}
