package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Client;

public interface ClientDao extends JpaRepository<Client, Integer> {}
