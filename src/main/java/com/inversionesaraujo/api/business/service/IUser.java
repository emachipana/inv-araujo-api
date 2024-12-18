package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.model.User;

public interface IUser {
    List<User> listAll();

    User save(User user);

    User findById(Integer id);

    User findByUsername(String username);

    void delete(User user);
}
