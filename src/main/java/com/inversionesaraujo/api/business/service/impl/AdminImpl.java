package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IAdmin;
import com.inversionesaraujo.api.model.Admin;
import com.inversionesaraujo.api.repository.AdminRepository;

@Service
public class AdminImpl implements IAdmin {
    @Autowired
    private AdminRepository adminRepo;

    @Transactional(readOnly = true)
    @Override
    public Admin findById(Integer id) {
        return adminRepo.findById(id).orElseThrow(() -> new DataAccessException("El administrador no existe") {});
    }

    @Transactional
    @Override
    public Admin save(Admin admin) {
        return adminRepo.save(admin);
    }

    @Transactional(readOnly = true)
    @Override
    public Admin findByEmail(String email) {
        return adminRepo.findByEmail(email);
    }
}
