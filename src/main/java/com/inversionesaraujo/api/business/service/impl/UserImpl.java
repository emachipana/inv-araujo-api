package com.inversionesaraujo.api.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.UserDTO;
import com.inversionesaraujo.api.business.service.IUser;
import com.inversionesaraujo.api.model.User;
import com.inversionesaraujo.api.repository.UserRepository;

@Service
public class UserImpl implements IUser {
    @Autowired
    private UserRepository userRepo;

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> listAll() {
        List<User> users = userRepo.findAll();

        return UserDTO.toListDTO(users);
    }

    @Transactional
    @Override
    public UserDTO save(UserDTO user) {
        User userSaved = userRepo.save(UserDTO.toEntity(user));

        return UserDTO.toDTO(userSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new DataAccessException("El usuario no existe") {}); 

        return UserDTO.toDTO(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new DataAccessException("El usuario no existe") {}); 

        return UserDTO.toDTO(user);
    }
}
