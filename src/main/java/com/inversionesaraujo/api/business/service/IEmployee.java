package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;

public interface IEmployee {
    List<EmployeeDTO> listAll();

    List<EmployeeDTO> search(String document, String rsocial, String email);

    EmployeeDTO save(EmployeeDTO employee);

    EmployeeDTO findById(Long id);

    EmployeeDTO findByEmail(String email);

    void delete(Long id);
}
