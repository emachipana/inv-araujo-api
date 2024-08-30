package com.inversionesaraujo.api.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);    
}
