package com.inversionesaraujo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inversionesaraujo.api.model.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    UserToken findByUserId(Long userId);

    @Query(value = "SELECT token FROM user_tokens ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLastToken();
}
