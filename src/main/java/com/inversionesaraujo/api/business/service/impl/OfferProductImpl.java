package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.OfferProductDTO;
import com.inversionesaraujo.api.business.service.IOfferProduct;
import com.inversionesaraujo.api.model.OfferProduct;
import com.inversionesaraujo.api.repository.OfferProductRepository;

@Service
public class OfferProductImpl implements IOfferProduct {
    @Autowired
    private OfferProductRepository productRepo;

    @Transactional
    @Override
    public OfferProductDTO save(OfferProductDTO item) {
        OfferProduct itemSaved = productRepo.save(OfferProductDTO.toEntity(item));

        return OfferProductDTO.toDTO(itemSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public OfferProductDTO findById(Long id) {
        OfferProduct item = productRepo.findById(id).orElseThrow(() -> new DataAccessException("El producto para el grupo de ofertas no existe") {});
        
        return OfferProductDTO.toDTO(item);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }
}
