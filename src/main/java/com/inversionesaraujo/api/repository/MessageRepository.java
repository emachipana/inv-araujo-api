package com.inversionesaraujo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String subject, String content, String email
    );
}
