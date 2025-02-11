package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.DiscountDTO;
import com.inversionesaraujo.api.business.service.IDiscount;
import com.inversionesaraujo.api.model.Discount;
import com.inversionesaraujo.api.repository.DiscountRepository;

@Service
public class DiscountImpl implements IDiscount {
    @Autowired
    private DiscountRepository discountRepo;

    @Transactional
    @Override
    public DiscountDTO save(DiscountDTO discount) {
        Discount discountSaved = discountRepo.save(DiscountDTO.toEntity(discount));

        return DiscountDTO.toDTO(discountSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public DiscountDTO findById(Long id) {
        Discount discount = discountRepo.findById(id).orElseThrow(() -> new DataAccessException("El descuento no existe") {});

        return DiscountDTO.toDTO(discount);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        discountRepo.deleteById(id);
    }

    @Override
    public Integer getPercentage(Double originalPrice, Double discountPrice) {
        return (int) Math.ceil(100 - ((100 * discountPrice) / originalPrice));
    }
}
