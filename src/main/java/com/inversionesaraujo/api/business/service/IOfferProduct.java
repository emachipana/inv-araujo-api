package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.OfferProductDTO;

public interface IOfferProduct {
    OfferProductDTO save(OfferProductDTO item);

    List<OfferProductDTO> findByOfferId(Long offerId);

    OfferProductDTO findById(Long id);

    void delete(Long id);
}
