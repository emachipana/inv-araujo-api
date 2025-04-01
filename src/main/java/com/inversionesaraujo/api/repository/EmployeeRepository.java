package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);

    List<Employee> findByDocumentContainingIgnoreCaseOrRsocialContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String document, String rsocial, String email
    );
}
