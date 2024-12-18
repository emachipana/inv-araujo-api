package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IOrderProduct;
import com.inversionesaraujo.api.model.OrderProduct;
import com.inversionesaraujo.api.repository.OrderProductRepository;

@Service
public class OrderProductImpl implements IOrderProduct {
    @Autowired
    private OrderProductRepository orderProductRepo;

    @Transactional
    @Override
    public OrderProduct save(OrderProduct item) {
        return orderProductRepo.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderProduct findById(Integer id) {
        return orderProductRepo.findById(id).orElseThrow(() -> new DataAccessException("El item del pedido no existe") {});
    }

    @Transactional
    @Override
    public void delete(OrderProduct item) {
        orderProductRepo.delete(item);
    }
}
