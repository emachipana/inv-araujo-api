package com.inversionesaraujo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.payload.MessageResponse;
import com.inversionesaraujo.api.business.request.RoleRequest;
import com.inversionesaraujo.api.business.service.IRole;
import com.inversionesaraujo.api.model.Permission;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private IRole roleService;

    @GetMapping
    public List<RoleDTO> getAll() {
        return roleService.findAll();
    }

    @GetMapping("/permissions")
    public List<Permission> getPermissions() {
        return List.of(Permission.values());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid RoleRequest request) {
        RoleDTO roleToSave = roleService.save(RoleDTO
            .builder()
            .name(request.getName())
            .permissions(request.getPermissions())
            .build());

        return ResponseEntity.status(201).body(MessageResponse
            .builder()
            .message("El rol se creo con exito")
            .data(roleToSave)
            .build());
    }
}
