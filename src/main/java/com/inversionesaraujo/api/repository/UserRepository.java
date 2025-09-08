package com.inversionesaraujo.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inversionesaraujo.api.model.Permission;
import com.inversionesaraujo.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    
    @Query("SELECT DISTINCT u FROM User u JOIN u.role r JOIN r.permissions p WHERE p = :permission AND u.id != :excludeUserId")
    List<User> findUsersWithPermissionExcludingUser(@Param("permission") Permission permission, @Param("excludeUserId") Long excludeUserId);
}
