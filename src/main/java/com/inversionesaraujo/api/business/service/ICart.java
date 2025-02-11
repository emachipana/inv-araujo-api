package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.CartDTO;

public interface ICart {
    CartDTO save(CartDTO cart);

    CartDTO findById(Long id);

    void delete(Long id);
}
