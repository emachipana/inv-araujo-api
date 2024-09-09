package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.VarietyDao;
import com.inversionesaraujo.api.model.entity.Variety;
import com.inversionesaraujo.api.service.IVariety;

@Service
public class VarietyImpl implements IVariety {
    @Autowired
    private VarietyDao varietyDao;

    @Transactional
    @Override
    public Variety save(Variety variety) {
        return varietyDao.save(variety);
    }

    @Transactional(readOnly = true)
    @Override
    public Variety findById(Integer id) {
        return varietyDao.findById(id).orElseThrow(() -> new DataAccessException("La variedad no existe") {});
    }

    @Transactional
    @Override
    public void delete(Variety variety) {
        varietyDao.delete(variety);
    }
}
