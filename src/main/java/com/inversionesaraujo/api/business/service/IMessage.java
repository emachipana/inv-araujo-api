package com.inversionesaraujo.api.business.service;

import org.springframework.data.domain.Page;

import com.inversionesaraujo.api.business.dto.MessageDTO;
import com.inversionesaraujo.api.model.SortDirection;

public interface IMessage {
    Page<MessageDTO> listAll(Integer page, Integer size, SortDirection direction);

    Page<MessageDTO> search(String subject, String content, String email, Integer page);

    MessageDTO save(MessageDTO message);

    MessageDTO findById(Long id);

    void delete(Long id);
}
