package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.VarietyDTO;
import com.inversionesaraujo.api.business.service.IVariety;
import com.inversionesaraujo.api.model.Variety;
import com.inversionesaraujo.api.repository.VarietyRepository;

@Service
public class VarietyImpl implements IVariety {
    @Autowired
    private VarietyRepository varietyRepo;

    @Transactional
    @Override
    public VarietyDTO save(VarietyDTO variety) {
        Variety varietySaved = varietyRepo.save(VarietyDTO.toEntity(variety));

        return VarietyDTO.toDTO(varietySaved);
    }

    @Transactional(readOnly = true)
    @Override
    public VarietyDTO findById(Long id) {
        Variety variety = varietyRepo.findById(id).orElseThrow(() -> new DataAccessException("La variedad no existe") {});

        return VarietyDTO.toDTO(variety);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        varietyRepo.deleteById(id);
    }
}
