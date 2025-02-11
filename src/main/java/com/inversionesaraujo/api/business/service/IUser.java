package com.inversionesaraujo.api.business.service;

import java.util.List;

import com.inversionesaraujo.api.business.dto.UserDTO;

public interface IUser {
    List<UserDTO> listAll();

    UserDTO save(UserDTO user);

    UserDTO findById(Long id);

    UserDTO findByUsername(String username);

    void delete(Long id);
}
