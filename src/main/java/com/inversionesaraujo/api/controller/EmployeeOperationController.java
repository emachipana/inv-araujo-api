package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;

@RestController
@RequestMapping("/api/v1/employee-operations")
public class EmployeeOperationController {
    @Autowired
    private IEmployeeOperation employeeOperationService;

    @GetMapping("/{employeeId}")
    public List<EmployeeOperationDTO> findByEmployeeId(@PathVariable Long employeeId) {
        return employeeOperationService.findByEmployeeId(employeeId);
    }
}
