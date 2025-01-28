package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.CartProduct;

public interface ICartProduct {
    CartProduct save(CartProduct item); 

    CartProduct findById(Integer id);

    void delete(CartProduct item);
}
