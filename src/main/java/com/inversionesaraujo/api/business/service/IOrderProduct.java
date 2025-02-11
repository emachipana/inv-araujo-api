package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.OrderProductDTO;

public interface IOrderProduct {
    OrderProductDTO save(OrderProductDTO item);

    OrderProductDTO findById(Long id);

    void delete(Long id);
}
