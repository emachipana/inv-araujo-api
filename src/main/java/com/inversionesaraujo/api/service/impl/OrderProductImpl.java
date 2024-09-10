package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.OrderProductDao;
import com.inversionesaraujo.api.model.entity.OrderProduct;
import com.inversionesaraujo.api.service.IOrderProduct;

@Service
public class OrderProductImpl implements IOrderProduct {
    @Autowired
    private OrderProductDao orderProductDao;

    @Transactional
    @Override
    public OrderProduct save(OrderProduct item) {
        return orderProductDao.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderProduct findById(Integer id) {
        return orderProductDao.findById(id).orElseThrow(() -> new DataAccessException("El item del pedido no existe") {});
    }

    @Transactional
    @Override
    public void delete(OrderProduct item) {
        orderProductDao.delete(item);
    }
}
