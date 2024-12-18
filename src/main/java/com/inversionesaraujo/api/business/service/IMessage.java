package com.inversionesaraujo.api.business.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.model.Message;
import com.inversionesaraujo.api.model.SortDirection;

public interface IMessage {
    Page<Message> listAll(Integer page, Integer size, SortDirection direction);

    List<Message> search(String subject, String content, String email);

    Message save(Message message);

    Message findById(Integer id);

    void delete(Message message);
}
