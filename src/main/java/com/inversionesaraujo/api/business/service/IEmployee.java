package com.inversionesaraujo.api.business.service;

import java.util.List;


import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;

public interface IEmployee {
    List<EmployeeDTO> filterEmployees(Long roleId, SortBy sortby, SortDirection direction);

    List<EmployeeDTO> search(String document, String rsocial, String email);

    EmployeeDTO save(EmployeeDTO employee);

    EmployeeDTO findById(Long id);

    EmployeeDTO findByEmail(String email);

    void delete(Long id);
}
