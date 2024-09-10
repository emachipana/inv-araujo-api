package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.OfferDao;
import com.inversionesaraujo.api.model.entity.Offer;
import com.inversionesaraujo.api.service.IOffer;

@Service
public class OfferImpl implements IOffer {
    @Autowired
    private OfferDao offerDao;

    @Transactional(readOnly = true)
    @Override
    public List<Offer> listAll() {
        return offerDao.findAll();
    }

    @Transactional
    @Override
    public Offer save(Offer offer) {
        return offerDao.save(offer);
    }

    @Transactional(readOnly = true)
    @Override
    public Offer findById(Integer id) {
        return offerDao.findById(id).orElseThrow(() -> new DataAccessException("La oferta no existe") {});
    }

    @Transactional
    @Override
    public void delete(Offer offer) {
        offerDao.delete(offer);
    }
}
