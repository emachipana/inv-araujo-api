package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.EmployeeOperationDTO;
import com.inversionesaraujo.api.business.service.IEmployeeOperation;
import com.inversionesaraujo.api.model.EmployeeOperation;
import com.inversionesaraujo.api.repository.EmployeeOperationRepository;

@Service
public class EmployeeOperationImpl implements IEmployeeOperation {
    @Autowired
    private EmployeeOperationRepository repo;

  @Override
  public List<EmployeeOperationDTO> findByEmployeeId(Long employeeId) {
    List<EmployeeOperation> employeeOperations = repo.findByEmployeeId(employeeId);

    return EmployeeOperationDTO.toListDTO(employeeOperations);
  }

  @Override
  public EmployeeOperationDTO save(EmployeeOperationDTO employeeOperation) {
    EmployeeOperation newEmployeeOperation = EmployeeOperationDTO.toEntity(employeeOperation);
    
    return EmployeeOperationDTO.toDTO(repo.save(newEmployeeOperation));
  }

  @Override
  public void delete(Long id) {
    repo.deleteById(id);
  }
}
