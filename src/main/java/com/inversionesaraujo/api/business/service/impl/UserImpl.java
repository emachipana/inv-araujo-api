package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IUser;
import com.inversionesaraujo.api.model.User;
import com.inversionesaraujo.api.repository.UserRepository;

@Service
public class UserImpl implements IUser {
    @Autowired
    private UserRepository userRepo;

    @Transactional(readOnly = true)
    @Override
    public List<User> listAll() {
        return userRepo.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        return userRepo.findById(id).orElseThrow(() -> new DataAccessException("El usuario no existe") {});
    }

    @Transactional
    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new DataAccessException("El usuario no existe") {});
    }
}
