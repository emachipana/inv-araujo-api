package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.CartProductDTO;

public interface ICartProduct {
    CartProductDTO save(CartProductDTO item); 

    CartProductDTO findById(Long id);

    void delete(Long id);
}
