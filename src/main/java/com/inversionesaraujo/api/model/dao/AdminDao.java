package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Admin;

public interface AdminDao extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
}
