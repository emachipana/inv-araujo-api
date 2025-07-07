package com.inversionesaraujo.api.business.dto;

import java.util.Set;

import com.inversionesaraujo.api.model.Permission;
import com.inversionesaraujo.api.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private Set<Permission> permissions;

    public static RoleDTO toDTO(Role role) {
        if(role == null) return null;

        return RoleDTO
            .builder()
            .id(role.getId())
            .name(role.getName())
            .permissions(role.getPermissions())
            .build();
    }

    public static Role toEntity(RoleDTO roleDTO) {
        if(roleDTO == null) return null;

        return Role
            .builder()
            .id(roleDTO.getId())
            .name(roleDTO.getName())
            .permissions(roleDTO.getPermissions())
            .build();
    }
}
