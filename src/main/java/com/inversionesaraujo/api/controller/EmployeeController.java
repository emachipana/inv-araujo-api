package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.payload.EmployeeResponse;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.EmployeeRequest;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.business.service.IRole;
import com.inversionesaraujo.api.business.service.IUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private IEmployee employeeService;
    @Autowired
    private IUser userService;
    @Autowired
    private IRole roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<EmployeeDTO> getAll() {
      return employeeService.listAll();
    }

    @GetMapping("/search")
    public List<EmployeeDTO> search(@RequestParam String param) {
        return employeeService.search(param, param, param);
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getOneById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.findById(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El empleado se encontro con exito")
            .data(employee)
            .build());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid EmployeeRequest request) {
        EmployeeDTO newEmployee = employeeService.save(EmployeeDTO
            .builder()
            .rsocial(request.getRsocial())
            .document(request.getDocument())
            .email(request.getEmail())
            .phone(request.getPhone())
            .build());

        RoleDTO role = roleService.findById(request.getRoleId());

        String defaultPassword = "12345678";
        UserDTO newUser = userService.save(UserDTO
			.builder()
			.role(role)
			.employeeId(newEmployee.getId())
			.username(newEmployee.getEmail())
            .password(passwordEncoder.encode(defaultPassword))
			.build());

		EmployeeResponse response = EmployeeResponse
			.builder()
			.employee(newEmployee)
			.user(newUser)
			.build();

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El empleado se creo con exito")
            .data(response)
            .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid EmployeeRequest request, @PathVariable Long id) {
        EmployeeDTO employee = employeeService.findById(id);
        employee.setPhone(request.getPhone());
        EmployeeDTO employeeUpdated = employeeService.save(employee);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El empleado se actualizo con exito")
            .data(employeeUpdated)
            .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.findById(id);
        employee.setUserId(null);
        employeeService.save(employee);

        userService.delete(employee.getUserId());
        employeeService.delete(id);

        return ResponseEntity.ok().body(MessageResponse
            .builder()
            .message("El empleado se eliminó con exito")
            .build());
    }
}
