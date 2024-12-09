package com.inversionesaraujo.api.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Message;

public interface MessageDao extends JpaRepository<Message, Integer> {
    List<Message> findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String subject, String content, String email
    );
}
