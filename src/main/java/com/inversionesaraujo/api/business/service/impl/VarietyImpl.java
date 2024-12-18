package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IVariety;
import com.inversionesaraujo.api.model.Variety;
import com.inversionesaraujo.api.repository.VarietyRepository;

@Service
public class VarietyImpl implements IVariety {
    @Autowired
    private VarietyRepository varietyRepo;

    @Transactional
    @Override
    public Variety save(Variety variety) {
        return varietyRepo.save(variety);
    }

    @Transactional(readOnly = true)
    @Override
    public Variety findById(Integer id) {
        return varietyRepo.findById(id).orElseThrow(() -> new DataAccessException("La variedad no existe") {});
    }

    @Transactional
    @Override
    public void delete(Variety variety) {
        varietyRepo.delete(variety);
    }
}
