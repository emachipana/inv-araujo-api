package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.OfferDTO;

public interface IOffer {
    List <OfferDTO> listAll();

    OfferDTO save(OfferDTO offer);

    OfferDTO findById(Long id);

    void delete(Long id);
}
