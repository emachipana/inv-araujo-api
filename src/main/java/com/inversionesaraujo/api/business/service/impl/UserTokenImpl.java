package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.service.IUserToken;
import com.inversionesaraujo.api.model.UserToken;
import com.inversionesaraujo.api.repository.UserTokenRepository;

@Service
public class UserTokenImpl implements IUserToken {
    @Autowired
    private UserTokenRepository repo;

    @Override
    public void saveOrUpdateToken(Long userId, String token) {
        UserToken newToken = UserToken
            .builder()
            .userId(userId)
            .token(token)
            .build();

        UserToken saved = repo.save(newToken);

        // UserToken existingToken = repo.findByUserId(userId);

        // if (existingToken != null) {
        //     existingToken.setToken(token);
        //     repo.save(existingToken);
        // } else {
        //     UserToken newToken = UserToken
        //         .builder()
        //         .userId(userId)
        //         .token(token)
        //         .build();

        //     repo.save(newToken);
        // }
    }
}
