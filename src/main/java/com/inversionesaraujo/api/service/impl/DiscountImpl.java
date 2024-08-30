package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.DiscountDao;
import com.inversionesaraujo.api.model.entity.Discount;
import com.inversionesaraujo.api.service.IDiscount;

@Service
public class DiscountImpl implements IDiscount {
    @Autowired
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
