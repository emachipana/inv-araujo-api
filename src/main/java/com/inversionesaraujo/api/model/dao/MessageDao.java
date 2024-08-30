package com.inversionesaraujo.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inversionesaraujo.api.model.entity.Message;

public interface MessageDao extends JpaRepository<Message, Integer> {}
