package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.Discount;

public interface IDiscount {
    Discount save(Discount discount);

    Discount findById(Integer id);

    void delete(Discount discount);

    Integer getPercentage(Double originalPrice, Double discountPrice);
}
