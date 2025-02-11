package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.CartDTO;
import com.inversionesaraujo.api.business.service.ICart;
import com.inversionesaraujo.api.model.Cart;
import com.inversionesaraujo.api.repository.CartRepository;

@Service
public class CartImpl implements ICart {
    @Autowired
    private CartRepository cartRepo;

    @Transactional
    @Override
    public CartDTO save(CartDTO cart) {
        Cart cartSaved = cartRepo.save(CartDTO.toEntity(cart));
        return CartDTO.toDTO(cartSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public CartDTO findById(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(() -> new DataAccessException("El carrito no existe") {});
        return CartDTO.toDTO(cart);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        cartRepo.deleteById(id);
    }
}
