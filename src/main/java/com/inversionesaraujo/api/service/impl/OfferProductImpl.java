package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.OfferProductDao;
import com.inversionesaraujo.api.model.entity.OfferProduct;
import com.inversionesaraujo.api.service.IOfferProduct;

@Service
public class OfferProductImpl implements IOfferProduct {
    @Autowired
    private OfferProductDao productDao;

    @Transactional
    @Override
    public OfferProduct save(OfferProduct item) {
        return productDao.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public OfferProduct findById(Integer id) {
        return productDao.findById(id).orElseThrow(() -> new DataAccessException("El producto para el grupo de ofertas no existe") {});
    }

    @Transactional
    @Override
    public void delete(OfferProduct item) {
        productDao.delete(item);
    }
}
