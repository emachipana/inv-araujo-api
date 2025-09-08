package com.inversionesaraujo.api.business.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Employee;
import com.inversionesaraujo.api.model.EmployeeOperation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeOperationDTO {
    private Long id;
    private String operation;
    private String redirectTo;
    private Long employeeId;
    private LocalDateTime createdAt;

    public static EmployeeOperationDTO toDTO(EmployeeOperation employeeOperation) {
        if(employeeOperation == null) return null;

        return EmployeeOperationDTO
            .builder()
            .id(employeeOperation.getId())
            .operation(employeeOperation.getOperation())
            .redirectTo(employeeOperation.getRedirectTo())
            .employeeId(employeeOperation.getEmployee().getId())
            .createdAt(employeeOperation.getCreatedAt())
            .build();
    }

    public static EmployeeOperation toEntity(EmployeeOperationDTO employeeOperation) {
        if(employeeOperation == null) return null;

        Employee employee = new Employee();
        employee.setId(employeeOperation.getEmployeeId());

        return EmployeeOperation
            .builder()
            .id(employeeOperation.getId())
            .operation(employeeOperation.getOperation())
            .redirectTo(employeeOperation.getRedirectTo())
            .employee(employee)
            .createdAt(employeeOperation.getCreatedAt())
            .build();
    }

    public static List<EmployeeOperationDTO> toListDTO(List<EmployeeOperation> employeeOperations) {
        return employeeOperations.stream().map(EmployeeOperationDTO::toDTO).collect(Collectors.toList());
    }
}
