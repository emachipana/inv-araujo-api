package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.Cart;

public interface ICart {
    Cart save(Cart cart);

    Cart findById(Integer id);

    void delete(Cart cart);
}
