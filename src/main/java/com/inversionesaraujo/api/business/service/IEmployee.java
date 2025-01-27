package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.model.Employee;

public interface IEmployee {
    List<Employee> listAll();

    Employee save(Employee employee);

    Employee findById(Integer id);

    Employee findByEmail(String email);

    void delete(Employee employee);
}
