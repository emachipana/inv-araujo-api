package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.EmployeeOperation;

public interface EmployeeOperationRepository extends JpaRepository<EmployeeOperation, Long> {
    List<EmployeeOperation> findByEmployeeId(Long employeeId);  
}
