package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.UserDao;
import com.inversionesaraujo.api.model.entity.User;
import com.inversionesaraujo.api.service.IUser;

@Service
public class UserImpl implements IUser {
    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public List<User> listAll() {
        return userDao.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        return userDao.findById(id).orElseThrow(() -> new DataAccessException("El usuario no existe") {});
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(() -> new DataAccessException("El usuario no existe") {});
    }
}
