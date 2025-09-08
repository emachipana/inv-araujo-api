package com.inversionesaraujo.api.business.request;

import java.util.Set;

import com.inversionesaraujo.api.model.Permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    private String name;
    private Set<Permission> permissions;
}
