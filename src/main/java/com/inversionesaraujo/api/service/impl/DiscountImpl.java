package com.inversionesaraujo.api.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.DiscountDao;
import com.inversionesaraujo.api.model.entity.Discount;
import com.inversionesaraujo.api.service.IDiscount;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscountImpl implements IDiscount {
    private DiscountDao discountDao;

    @Transactional
    @Override
    public Discount save(Discount discount) {
        return discountDao.save(discount);
    }

    @Transactional(readOnly = true)
    @Override
    public Discount findById(Integer id) {
        return discountDao.findById(id).orElseThrow(() -> new DataAccessException("El descuento no existe") {});
    }

    @Transactional
    @Override
    public void delete(Discount discount) {
        discountDao.delete(discount);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return discountDao.existsById(id);
    }
}
