package com.inversionesaraujo.api.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ResetDao;
import com.inversionesaraujo.api.model.entity.Reset;
import com.inversionesaraujo.api.service.IReset;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResetImpl implements IReset {
    private ResetDao resetDao;

    @Transactional
    @Override
    public Reset save(Reset reset) {
        return resetDao.save(reset);
    }

    @Transactional(readOnly = true)
    @Override
    public Reset findById(Integer id) {
        return resetDao.findById(id).orElseThrow(() -> new DataAccessException("El codigo de reset no existe") {});
    }

    @Transactional
    @Override
    public void delete(Reset reset) {
        resetDao.delete(reset);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return resetDao.existsById(id);
    }
}
