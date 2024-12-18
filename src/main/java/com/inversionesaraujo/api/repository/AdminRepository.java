package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
}
