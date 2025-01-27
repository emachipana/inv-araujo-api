package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.model.Employee;
import com.inversionesaraujo.api.repository.EmployeeRepository;

@Service
public class EmployeeImpl implements IEmployee {
    @Autowired
    private EmployeeRepository employeeRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Employee> listAll() {
        return employeeRepo.findAll();
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findById(Integer id) {
        return employeeRepo.findById(id).orElseThrow(() -> new DataAccessException("El empleado no existe") {});
    }

    @Transactional
    @Override
    public void delete(Employee employee) {
        employeeRepo.delete(employee);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepo.findByEmail(email);
    }
}
