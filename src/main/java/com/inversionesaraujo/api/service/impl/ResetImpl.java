package com.inversionesaraujo.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ResetDao;
import com.inversionesaraujo.api.model.entity.Reset;
import com.inversionesaraujo.api.service.IReset;

@Service
public class ResetImpl implements IReset {
    @Autowired
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
}
