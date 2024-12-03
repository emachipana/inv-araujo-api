package com.inversionesaraujo.api.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Client;

public interface ClientDao extends JpaRepository<Client, Integer> {
    List<Client> 
    findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCaseOrCityContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
        String rsocial, String document, String city, String department
    );
}
