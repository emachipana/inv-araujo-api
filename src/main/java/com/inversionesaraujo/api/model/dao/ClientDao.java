package com.inversionesaraujo.api.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Client;

public interface ClientDao extends JpaRepository<Client, Integer> {
    Optional<Client> findById(Integer id);
}
