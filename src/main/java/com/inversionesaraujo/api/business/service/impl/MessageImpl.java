package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Message> listAll(Integer page, Integer size, SortDirection direction) {
        Pageable pageable;
        if(direction != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "id");
            pageable = PageRequest.of(page, size, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }
        
        return messageRepo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Message> search(String subject, String content, String email, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        return messageRepo
            .findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCaseOrEmailContainingIgnoreCase(
                subject, content, email, pageable
            );
    }

    @Transactional
    @Override
    public Message save(Message message) {
        return messageRepo.save(message);
    }

    @Transactional(readOnly = true)
    @Override
    public Message findById(Integer id) {
        return messageRepo.findById(id).orElseThrow(() -> new DataAccessException("El mensaje no existe") {});
    }

    @Transactional
    @Override
    public void delete(Message message) {
        messageRepo.delete(message);
    }
}
