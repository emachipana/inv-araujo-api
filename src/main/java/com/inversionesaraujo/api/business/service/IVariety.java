package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.VarietyDTO;

public interface IVariety {
    VarietyDTO save(VarietyDTO variety);

    List<VarietyDTO> findByTuberId(Long tuberId);

    VarietyDTO findById(Long id);

    void delete(Long id);
}
