package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.OfferProductDTO;

public interface IOfferProduct {
    OfferProductDTO save(OfferProductDTO item);

    OfferProductDTO findById(Long id);

    void delete(Long id);
}
