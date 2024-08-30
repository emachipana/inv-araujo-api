package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.Discount;

public interface IDiscount {
    Discount save(Discount discount);

    Discount findById(Integer id);

    void delete(Discount discount);
}
