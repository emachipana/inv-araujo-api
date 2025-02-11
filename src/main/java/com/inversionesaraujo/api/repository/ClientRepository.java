package com.inversionesaraujo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> 
    findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCaseOrCityContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
        String rsocial, String document, String city, String department, Pageable pageable
    );
}
