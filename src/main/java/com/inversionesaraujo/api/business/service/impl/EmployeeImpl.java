package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.business.spec.EmployeeSpecification;
import com.inversionesaraujo.api.model.Employee;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.EmployeeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class EmployeeImpl implements IEmployee {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private EmployeeOperationImpl employeeOperationService;

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> filterEmployees(Long roleId, SortBy sortby, SortDirection direction) {
        Specification<Employee> spec = Specification.where(
            EmployeeSpecification.belongsToRole(roleId)
        );

        Sort sort = Sort.unsorted();
        if(sortby != null) {
            sort = Sort.by(Sort.Direction.fromString(direction.toString()), sortby.toString());
        }

        List<Employee> employees = employeeRepo.findAll(spec, sort);
        return EmployeeDTO.toListDTO(employees);
    }

    @Transactional
    @Override
    public EmployeeDTO save(EmployeeDTO employee) {
        Employee employeeSaved = employeeRepo.save(EmployeeDTO.toEntity(employee, entityManager));

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
        List<EmployeeOperationDTO> operations = employeeOperationService.findByEmployeeId(id);
        for(EmployeeOperationDTO operation : operations) {
            employeeOperationService.delete(operation.getId());
        }

        employeeRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO findByEmail(String email) {
        Employee employee = employeeRepo.findByEmail(email);
        if(employee == null) return null;

        return EmployeeDTO.toDTO(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> search(String document, String rsocial, String email) {
       List<Employee> employees = employeeRepo.findByDocumentContainingIgnoreCaseOrRsocialContainingIgnoreCaseOrEmailContainingIgnoreCase(document, rsocial, email);

       return EmployeeDTO.toListDTO(employees);
    }
}
