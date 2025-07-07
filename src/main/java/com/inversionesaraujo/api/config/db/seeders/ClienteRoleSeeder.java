package com.inversionesaraujo.api.config.db.seeders;

import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.service.IRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteRoleSeeder {
    @Autowired
    private IRole roleService;

    public void seed() {
        final String roleName = "CLIENTE";

        RoleDTO existing = roleService.findByName(roleName);
        if (existing == null) {
            RoleDTO role = RoleDTO
                .builder()
                .name(roleName)
                .permissions(java.util.Collections.emptySet())
                .build();
            roleService.save(role);
            System.out.println("Rol CLIENTE creado sin permisos.");
        } else {
            System.out.println("Rol CLIENTE ya existe.");
        }
    }
}
