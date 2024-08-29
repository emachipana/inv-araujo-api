package com.inversionesaraujo.api.service;

import com.inversionesaraujo.api.model.entity.Admin;

public interface IAdmin {
    Admin findById(Integer id);

    Admin save(Admin admin);

    boolean ifExists(Integer id);
}
