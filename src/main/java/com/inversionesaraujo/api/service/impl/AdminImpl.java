package com.inversionesaraujo.api.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.AdminDao;
import com.inversionesaraujo.api.model.entity.Admin;
import com.inversionesaraujo.api.service.IAdmin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminImpl implements IAdmin {
    private AdminDao adminDao;

    @Transactional(readOnly = true)
    @Override
    public Admin findById(Integer id) {
        return adminDao.findById(id).orElseThrow(() -> new DataAccessException("El administrador no existe") {});
    }

    @Transactional
    @Override
    public Admin save(Admin admin) {
        return adminDao.save(admin);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return adminDao.existsById(id);
    }
}
