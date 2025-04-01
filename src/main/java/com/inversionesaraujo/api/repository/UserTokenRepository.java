package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inversionesaraujo.api.model.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findByUserId(Long userId);

    @Query("SELECT t.token FROM UserToken t ORDER BY t.id DESC LIMIT 1")
    String findLastToken();
}
