package com.inversionesaraujo.api.business.service;

public interface IUserToken {
    void saveOrUpdateToken(Long userId, String token);    
}
