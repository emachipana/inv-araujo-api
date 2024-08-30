package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.LandingOfferDao;
import com.inversionesaraujo.api.model.entity.LandingOffer;
import com.inversionesaraujo.api.service.ILandingOffer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingOfferImpl implements ILandingOffer {
    private LandingOfferDao landingOfferDao;

    @Transactional(readOnly = true)
    @Override
    public List<LandingOffer> listAll() {
        return landingOfferDao.findAll();
    }

    @Transactional
    @Override
    public LandingOffer save(LandingOffer offer) {
        return landingOfferDao.save(offer);
    }

    @Transactional(readOnly = true)
    @Override
    public LandingOffer findById(Integer id) {
        return landingOfferDao.findById(id).orElseThrow(() -> new DataAccessException("La oferta no existe") {});
    }

    @Transactional
    @Override
    public void delete(LandingOffer offer) {
        landingOfferDao.delete(offer);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return landingOfferDao.existsById(id);
    }
}
