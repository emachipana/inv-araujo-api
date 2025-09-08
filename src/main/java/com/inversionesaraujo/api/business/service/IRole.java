package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.RoleDTO;

public interface IRole {
    RoleDTO save(RoleDTO roleDTO);

    void delete(Long id);

    RoleDTO findById(Long id);

    RoleDTO findByName(String name);

    List<RoleDTO> findAll();
}
