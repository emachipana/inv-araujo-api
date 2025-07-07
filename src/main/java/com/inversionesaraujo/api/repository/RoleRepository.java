package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
