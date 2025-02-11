package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.OfferDTO;
import com.inversionesaraujo.api.business.service.IOffer;
import com.inversionesaraujo.api.model.Offer;
import com.inversionesaraujo.api.repository.OfferRepository;

@Service
public class OfferImpl implements IOffer {
    @Autowired
    private OfferRepository offerRepo;

    @Transactional(readOnly = true)
    @Override
    public List<OfferDTO> listAll() {
        List<Offer> offers = offerRepo.findAll();

        return OfferDTO.toListDTO(offers);
    }

    @Transactional
    @Override
    public OfferDTO save(OfferDTO offer) {
        Offer offerSaved = offerRepo.save(OfferDTO.toEntity(offer));

        return OfferDTO.toDTO(offerSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public OfferDTO findById(Long id) {
        Offer offer = offerRepo.findById(id).orElseThrow(() -> new DataAccessException("La oferta no existe") {});

        return OfferDTO.toDTO(offer);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        offerRepo.deleteById(id);
    }
}
