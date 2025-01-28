package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.ICartProduct;
import com.inversionesaraujo.api.model.CartProduct;
import com.inversionesaraujo.api.repository.CartProductRepository;

@Service
public class CartProductImpl implements ICartProduct {
    @Autowired
    private CartProductRepository cartProductRepo;

    @Transactional
    @Override
    public CartProduct save(CartProduct item) {
        return cartProductRepo.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public CartProduct findById(Integer id) {
        return cartProductRepo.findById(id).orElseThrow(() -> new DataAccessException("El producto del carrito no se encuentra") {});
    }

    @Transactional
    @Override
    public void delete(CartProduct item) {
        cartProductRepo.delete(item);
    }    
}
