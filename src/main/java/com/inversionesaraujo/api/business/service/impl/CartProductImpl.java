package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.CartProductDTO;
import com.inversionesaraujo.api.business.service.ICartProduct;
import com.inversionesaraujo.api.model.CartProduct;
import com.inversionesaraujo.api.repository.CartProductRepository;

@Service
public class CartProductImpl implements ICartProduct {
    @Autowired
    private CartProductRepository cartProductRepo;

    @Transactional
    @Override
    public CartProductDTO save(CartProductDTO item) {
        CartProduct itemSaved = cartProductRepo.save(CartProductDTO.toEntity(item));

        return CartProductDTO.toDTO(itemSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public CartProductDTO findById(Long id) {
        CartProduct cartProduct = cartProductRepo.findById(id).orElseThrow(() -> new DataAccessException("El producto del carrito no se encuentra") {});

        return CartProductDTO.toDTO(cartProduct);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        cartProductRepo.deleteById(id);
    }    
}
