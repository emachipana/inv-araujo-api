package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.MessageDTO;
import com.inversionesaraujo.api.business.service.IMessage;
import com.inversionesaraujo.api.model.Message;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.MessageRepository;

@Service
public class MessageImpl implements IMessage {
    @Autowired
    private MessageRepository messageRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<MessageDTO> listAll(Integer page, Integer size, SortDirection direction) {
        Pageable pageable;
        if(direction != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "id");
            pageable = PageRequest.of(page, size, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }
        
        Page<Message> messages = messageRepo.findAll(pageable);

        return MessageDTO.toPageableDTO(messages);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MessageDTO> search(String subject, String content, String email, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Message> messages = messageRepo
            .findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCaseOrEmailContainingIgnoreCase(
                subject, content, email, pageable
            );

        return MessageDTO.toPageableDTO(messages);
    }

    @Transactional
    @Override
    public MessageDTO save(MessageDTO message) {
        Message messageSaved = messageRepo.save(MessageDTO.toEntity(message));

        return MessageDTO.toDTO(messageSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public MessageDTO findById(Long id) {
        Message message = messageRepo.findById(id).orElseThrow(() -> new DataAccessException("El mensaje no existe") {});

        return MessageDTO.toDTO(message);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        messageRepo.deleteById(id);
    }
}
