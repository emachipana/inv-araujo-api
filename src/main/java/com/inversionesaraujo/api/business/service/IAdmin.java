package com.inversionesaraujo.api.business.service;

import com.inversionesaraujo.api.model.Admin;

public interface IAdmin {
    Admin findById(Integer id);

    Admin findByEmail(String email);

    Admin save(Admin admin);
}
