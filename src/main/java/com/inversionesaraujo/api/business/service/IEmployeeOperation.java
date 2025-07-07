package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;

public interface IEmployeeOperation {
    List<EmployeeOperationDTO> findByEmployeeId(Long employeeId);
    
    EmployeeOperationDTO save(EmployeeOperationDTO employeeOperation);

    void delete(Long id);
}
