package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.OrderProductDTO;
import com.inversionesaraujo.api.business.service.IOrderProduct;
import com.inversionesaraujo.api.model.OrderProduct;
import com.inversionesaraujo.api.repository.OrderProductRepository;

@Service
public class OrderProductImpl implements IOrderProduct {
    @Autowired
    private OrderProductRepository orderProductRepo;

    @Transactional
    @Override
    public OrderProductDTO save(OrderProductDTO item) {
        OrderProduct itemSaved = orderProductRepo.save(OrderProductDTO.toEntity(item));

        return OrderProductDTO.toDTO(itemSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderProductDTO findById(Long id) {
        OrderProduct item = orderProductRepo.findById(id).orElseThrow(() -> new DataAccessException("El item del pedido no existe") {});

        return OrderProductDTO.toDTO(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderProductRepo.deleteById(id);
    }
}
