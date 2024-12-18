package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.OrderProduct;

public interface IOrderProduct {
    OrderProduct save(OrderProduct item);

    OrderProduct findById(Integer id);

    void delete(OrderProduct item);
}
