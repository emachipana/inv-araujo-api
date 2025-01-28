package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.ICart;
import com.inversionesaraujo.api.model.Cart;
import com.inversionesaraujo.api.repository.CartRepository;

@Service
public class CartImpl implements ICart {
    @Autowired
    private CartRepository cartRepo;

    @Transactional
    @Override
    public Cart save(Cart cart) {
        return cartRepo.save(cart);
    }

    @Transactional(readOnly = true)
    @Override
    public Cart findById(Integer id) {
        return cartRepo.findById(id).orElseThrow(() -> new DataAccessException("El carrito no existe") {});
    }

    @Transactional
    @Override
    public void delete(Cart cart) {
        cartRepo.delete(cart);
    }
}
