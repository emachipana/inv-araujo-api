package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.OrderVarietyDTO;
import com.inversionesaraujo.api.business.service.IOrderVariety;
import com.inversionesaraujo.api.model.OrderVariety;
import com.inversionesaraujo.api.repository.OrderVarietyRepository;

@Service
public class OrderVarietyImpl implements IOrderVariety {
    @Autowired
    private OrderVarietyRepository orderVarietyRepo;

    @Transactional
    @Override
    public OrderVarietyDTO save(OrderVarietyDTO item) {
        OrderVariety itemSaved = orderVarietyRepo.save(OrderVarietyDTO.toEntity(item));

        return OrderVarietyDTO.toDTO(itemSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderVarietyDTO findById(Long id) {
        OrderVariety item = orderVarietyRepo.findById(id).orElseThrow(() -> new DataAccessException("La variedad del pedido no existe") {});

        return OrderVarietyDTO.toDTO(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderVarietyRepo.deleteById(id);
    }
}
