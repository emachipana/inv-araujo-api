package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IDiscount;
import com.inversionesaraujo.api.model.Discount;
import com.inversionesaraujo.api.repository.DiscountRepository;

@Service
public class DiscountImpl implements IDiscount {
    @Autowired
    private DiscountRepository discountRepo;

    @Transactional
    @Override
    public Discount save(Discount discount) {
        return discountRepo.save(discount);
    }

    @Transactional(readOnly = true)
    @Override
    public Discount findById(Integer id) {
        return discountRepo.findById(id).orElseThrow(() -> new DataAccessException("El descuento no existe") {});
    }

    @Transactional
    @Override
    public void delete(Discount discount) {
        discountRepo.delete(discount);
    }

    @Override
    public Integer getPercentage(Double originalPrice, Double discountPrice) {
        return (int) Math.ceil(100 - ((100 * discountPrice) / originalPrice));
    }
}
