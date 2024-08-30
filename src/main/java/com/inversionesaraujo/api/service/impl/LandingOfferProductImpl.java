package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.LandingOfferProductDao;
import com.inversionesaraujo.api.model.entity.LandingOfferProduct;
import com.inversionesaraujo.api.service.ILandingOfferProduct;

@Service
public class LandingOfferProductImpl implements ILandingOfferProduct {
    @Autowired
    private LandingOfferProductDao productDao;

    @Transactional
    @Override
    public LandingOfferProduct save(LandingOfferProduct item) {
        return productDao.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public LandingOfferProduct findById(Integer id) {
        return productDao.findById(id).orElseThrow(() -> new DataAccessException("El producto para el grupo de ofertas no existe") {});
    }

    @Transactional
    @Override
    public void delete(LandingOfferProduct item) {
        productDao.delete(item);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return productDao.existsById(id);
    }
}
