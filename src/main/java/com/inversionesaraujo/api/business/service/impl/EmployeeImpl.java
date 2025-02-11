package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.model.Employee;
import com.inversionesaraujo.api.repository.EmployeeRepository;

@Service
public class EmployeeImpl implements IEmployee {
    @Autowired
    private EmployeeRepository employeeRepo;

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> listAll() {
        List<Employee> employees = employeeRepo.findAll();

        return EmployeeDTO.toListDTO(employees);
    }

    @Transactional
    @Override
    public EmployeeDTO save(EmployeeDTO employee) {
        Employee employeeSaved = employeeRepo.save(EmployeeDTO.toEntity(employee));

        return EmployeeDTO.toDTO(employeeSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO findById(Long id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new DataAccessException("El empleado no existe") {});

        return EmployeeDTO.toDTO(employee);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public EmployeeDTO findByEmail(String email) {
        Employee employee = employeeRepo.findByEmail(email);
        if(employee == null) return null;

        return EmployeeDTO.toDTO(employee);
    }
}
