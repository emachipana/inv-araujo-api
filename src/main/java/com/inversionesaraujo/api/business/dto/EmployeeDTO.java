package com.inversionesaraujo.api.business.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inversionesaraujo.api.model.Employee;
import com.inversionesaraujo.api.model.User;

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
public class EmployeeDTO {
    private Long id;
    private String rsocial;
    private String document;
    private String email;
    private String phone;
    private Long userId;

    public static EmployeeDTO toDTO(Employee employee) {
        if(employee == null) return null;

        return EmployeeDTO
            .builder()
            .id(employee.getId())
            .rsocial(employee.getRsocial())
            .document(employee.getDocument())
            .email(employee.getEmail())
            .phone(employee.getPhone())
            .userId(employee.getUser() != null ? employee.getUser().getId() : null)
            .build();
    }

    public static Employee toEntity(EmployeeDTO employee) {
        if(employee == null) return null;

        User user = new User();
        if(employee.getUserId() != null) user.setId(employee.getUserId());
        else user = null;

        return Employee
            .builder()
            .id(employee.getId())
            .rsocial(employee.getRsocial())
            .document(employee.getDocument())
            .email(employee.getEmail())
            .phone(employee.getPhone())
            .user(user)
            .build();
    }

    public static List<EmployeeDTO> toListDTO(List<Employee> employees) {
        return employees
            .stream()
            .map(EmployeeDTO::toDTO)
            .collect(Collectors.toList());
    }
}
