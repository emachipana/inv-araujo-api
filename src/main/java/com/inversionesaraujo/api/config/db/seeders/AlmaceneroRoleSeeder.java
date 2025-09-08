package com.inversionesaraujo.api.config.db.seeders;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.service.IRole;
import com.inversionesaraujo.api.model.Permission;

@Component
public class AlmaceneroRoleSeeder {
    @Autowired
    private IRole roleService;

    public void seed() {
        final String roleName = "ALMACENERO";

        RoleDTO existing = roleService.findByName(roleName);
        if (existing == null) {
            RoleDTO role = RoleDTO
                .builder()
                .name(roleName)
                .permissions(EnumSet.allOf(Permission.class))
                .build();
            roleService.save(role);
            System.out.println("Rol ALMACENERO creado con todos los permisos.");
        } else {
            boolean updated = existing.getPermissions().addAll(EnumSet.allOf(Permission.class));
            if (updated) {
                roleService.save(existing);
                System.out.println("Rol ALMACENERO actualizado con nuevos permisos.");
            } else {
                System.out.println("Rol ALMACENERO ya existe con todos los permisos.");
            }
        }
    }
}
