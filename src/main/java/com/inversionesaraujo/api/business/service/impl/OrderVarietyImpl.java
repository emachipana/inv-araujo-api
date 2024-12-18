package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IOrderVariety;
import com.inversionesaraujo.api.model.OrderVariety;
import com.inversionesaraujo.api.repository.OrderVarietyRepository;

@Service
public class OrderVarietyImpl implements IOrderVariety {
    @Autowired
    private OrderVarietyRepository orderVarietyRepo;

    @Transactional
    @Override
    public OrderVariety save(OrderVariety item) {
        return orderVarietyRepo.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderVariety findById(Integer id) {
        return orderVarietyRepo.findById(id).orElseThrow(() -> new DataAccessException("La variedad del pedido no existe") {});
    }

    @Transactional
    @Override
    public void delete(OrderVariety item) {
        orderVarietyRepo.delete(item);
    }
}
