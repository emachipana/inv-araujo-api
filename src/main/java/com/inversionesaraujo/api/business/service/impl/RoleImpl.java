package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.dto.RoleDTO;
import com.inversionesaraujo.api.business.service.IRole;
import com.inversionesaraujo.api.model.Role;
import com.inversionesaraujo.api.repository.RoleRepository;

@Service
public class RoleImpl implements IRole {
    @Autowired
    private RoleRepository roleRepo;

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Role roleSaved = roleRepo.save(RoleDTO.toEntity(roleDTO)); 

        return RoleDTO.toDTO(roleSaved);
    }

    @Override
    public void delete(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role = roleRepo.findById(id).orElseThrow(() -> new DataAccessException("El rol no existe") {});

        return RoleDTO.toDTO(role);
    }

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roles = roleRepo.findAll();

        return roles.stream().map(RoleDTO::toDTO).toList();
    }

    @Override
    public RoleDTO findByName(String name) {
        Role role = roleRepo.findByName(name);

        return RoleDTO.toDTO(role);
    }
    
}
