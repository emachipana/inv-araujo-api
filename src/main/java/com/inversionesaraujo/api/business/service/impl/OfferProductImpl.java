package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IOfferProduct;
import com.inversionesaraujo.api.model.OfferProduct;
import com.inversionesaraujo.api.repository.OfferProductRepository;

@Service
public class OfferProductImpl implements IOfferProduct {
    @Autowired
    private OfferProductRepository productRepo;

    @Transactional
    @Override
    public OfferProduct save(OfferProduct item) {
        return productRepo.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public OfferProduct findById(Integer id) {
        return productRepo.findById(id).orElseThrow(() -> new DataAccessException("El producto para el grupo de ofertas no existe") {});
    }

    @Transactional
    @Override
    public void delete(OfferProduct item) {
        productRepo.delete(item);
    }
}
