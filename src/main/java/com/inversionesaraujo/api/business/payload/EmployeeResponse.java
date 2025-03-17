package com.inversionesaraujo.api.business.payload;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {
    private UserDTO user;
    private EmployeeDTO employee;
}
