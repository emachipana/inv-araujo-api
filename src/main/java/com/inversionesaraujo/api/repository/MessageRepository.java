package com.inversionesaraujo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String subject, String content, String email, Pageable pageable
    );
}
