package com.inversionesaraujo.api.config.db.seeders;

import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.service.IRole;
import com.inversionesaraujo.api.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class AdminRoleSeeder {
    @Autowired
    private IRole roleService;

    public void seed() {
        final String roleName = "ADMINISTRADOR";

        RoleDTO existing = roleService.findByName(roleName);
        if (existing == null) {
            RoleDTO role = RoleDTO
                .builder()
                .name(roleName)
                .permissions(EnumSet.allOf(Permission.class))
                .build();
            roleService.save(role);
            System.out.println("Rol ADMINISTRADOR creado con todos los permisos.");
        } else {
            boolean updated = existing.getPermissions().addAll(EnumSet.allOf(Permission.class));
            if (updated) {
                roleService.save(existing);
                System.out.println("Rol ADMINISTRADOR actualizado con nuevos permisos.");
            } else {
                System.out.println("Rol ADMINISTRADOR ya existe con todos los permisos.");
            }
        }
    }
}
