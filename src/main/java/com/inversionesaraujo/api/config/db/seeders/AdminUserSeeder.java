package com.inversionesaraujo.api.config.db.seeders;

import com.inversionesaraujo.api.business.dto.EmployeeDTO;
import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.service.IEmployee;
import com.inversionesaraujo.api.business.service.IRole;
import com.inversionesaraujo.api.business.service.IUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserSeeder {
    @Autowired
    private IEmployee employeeService;
    @Autowired
    private IUser userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRole roleService;

    @Value("${ADMIN_EMAIL}")
    private String email;

    @Value("${ADMIN_DOCUMENT}")
    private String document;

    @Value("${ADMIN_PHONE}")
    private String phone;

    @Value("${ADMIN_PASSWORD}")
    private String rawPassword;

    public void seed() {
        EmployeeDTO existing = employeeService.findByEmail(email);
        if (existing != null) {
            System.out.println("Administrador ya existe.");
            return;
        }

        RoleDTO adminRole = roleService.findByName("ADMINISTRADOR");

        EmployeeDTO savedAdmin = employeeService.save(EmployeeDTO.builder()
            .email(email)
            .document(document)
            .rsocial("YURFA ARAUJO ESTRADA")
            .phone(phone)
            .build());

        UserDTO savedUser = userService.save(UserDTO.builder()
            .employeeId(savedAdmin.getId())
            .username(email)
            .password(passwordEncoder.encode(rawPassword))
            .role(adminRole)
            .isVerified(true)
            .build());

        if (savedUser != null) {
            System.out.println("Usuario administrador creado.");
        } else {
            System.out.println("Error al crear usuario administrador.");
        }
    }
}
