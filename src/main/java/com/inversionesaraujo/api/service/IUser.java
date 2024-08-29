package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.User;

public interface IUser {
    List<User> listAll();

    User save(User user);

    User findById(Integer id);

    void delete(User user);

    boolean ifExists(Integer id);
}
