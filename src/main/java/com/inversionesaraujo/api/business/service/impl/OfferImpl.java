package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IOffer;
import com.inversionesaraujo.api.model.Offer;
import com.inversionesaraujo.api.repository.OfferRepository;

@Service
public class OfferImpl implements IOffer {
    @Autowired
    private OfferRepository offerRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Offer> listAll() {
        return offerRepo.findAll();
    }

    @Transactional
    @Override
    public Offer save(Offer offer) {
        return offerRepo.save(offer);
    }

    @Transactional(readOnly = true)
    @Override
    public Offer findById(Integer id) {
        return offerRepo.findById(id).orElseThrow(() -> new DataAccessException("La oferta no existe") {});
    }

    @Transactional
    @Override
    public void delete(Offer offer) {
        offerRepo.delete(offer);
    }
}
