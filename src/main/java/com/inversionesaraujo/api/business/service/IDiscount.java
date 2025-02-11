package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.DiscountDTO;

public interface IDiscount {
    DiscountDTO save(DiscountDTO discount);

    DiscountDTO findById(Long id);

    void delete(Long id);

    Integer getPercentage(Double originalPrice, Double discountPrice);
}
