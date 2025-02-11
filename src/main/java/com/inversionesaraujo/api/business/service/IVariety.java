package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.business.dto.VarietyDTO;

public interface IVariety {
    VarietyDTO save(VarietyDTO variety);

    VarietyDTO findById(Long id);

    void delete(Long id);
}
