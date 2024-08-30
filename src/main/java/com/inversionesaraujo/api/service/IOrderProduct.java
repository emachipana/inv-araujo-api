package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.OrderProduct;

public interface IOrderProduct {
    OrderProduct save(OrderProduct item);

    OrderProduct findById(Integer id);

    void delete(OrderProduct item);

    boolean ifExists(Integer id);
}
