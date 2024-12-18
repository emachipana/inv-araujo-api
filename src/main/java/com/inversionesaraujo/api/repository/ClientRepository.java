package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> 
    findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCaseOrCityContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
        String rsocial, String document, String city, String department
    );
}
