package com.inversionesaraujo.api.service.impl;

import java.util.Random;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    @Override
    public String generateCode() {
        String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int size = 6;
        StringBuilder code = new StringBuilder(size);
        Random random = new Random();

        for(int i = 0; i < size; i++) {
            int ranIndex = random.nextInt(values.length());
            code.append(values.charAt(ranIndex));
        }

        return code.toString();
    }

    @Override
    public Boolean validCode(Reset reset, String code) throws Exception {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));

        if(!code.equals(reset.getCode())) return false;

        if(now.isAfter(reset.getExpiresAt())) throw new Exception("El codigo expiro");

        return true;
    }
}
