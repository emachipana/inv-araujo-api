package com.inversionesaraujo.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_tokens")
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_token_seq")
    @SequenceGenerator(name = "user_token_seq", sequenceName = "user_token_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private String token;
}
