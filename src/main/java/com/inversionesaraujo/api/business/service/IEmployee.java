package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;

public interface IEmployee {
    List<EmployeeDTO> listAll();

    EmployeeDTO save(EmployeeDTO employee);

    EmployeeDTO findById(Long id);

    EmployeeDTO findByEmail(String email);

    void delete(Long id);
}
