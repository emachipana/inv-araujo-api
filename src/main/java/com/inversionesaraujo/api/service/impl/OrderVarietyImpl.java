package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.OrderVarietyDao;
import com.inversionesaraujo.api.model.entity.OrderVariety;
import com.inversionesaraujo.api.service.IOrderVariety;

@Service
public class OrderVarietyImpl implements IOrderVariety {
    @Autowired
    private OrderVarietyDao orderVarietyDao;

    @Transactional
    @Override
    public OrderVariety save(OrderVariety item) {
        return orderVarietyDao.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderVariety findById(Integer id) {
        return orderVarietyDao.findById(id).orElseThrow(() -> new DataAccessException("La variedad del pedido no existe") {});
    }

    @Transactional
    @Override
    public void delete(OrderVariety item) {
        orderVarietyDao.delete(item);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return orderVarietyDao.existsById(id);
    }
}
