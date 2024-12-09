package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.MessageDao;
import com.inversionesaraujo.api.model.entity.Message;
import com.inversionesaraujo.api.model.entity.SortDirection;
import com.inversionesaraujo.api.service.IMessage;

@Service
public class MessageImpl implements IMessage {
    @Autowired
    private MessageDao messageDao;

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
        
        return messageDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Message> search(String subject, String content, String email) {
        return messageDao
            .findBySubjectContainingIgnoreCaseOrContentContainingIgnoreCaseOrEmailContainingIgnoreCase(
                subject, content, email
            );
    }

    @Transactional
    @Override
    public Message save(Message message) {
        return messageDao.save(message);
    }

    @Transactional(readOnly = true)
    @Override
    public Message findById(Integer id) {
        return messageDao.findById(id).orElseThrow(() -> new DataAccessException("El mensaje no existe") {});
    }

    @Transactional
    @Override
    public void delete(Message message) {
        messageDao.delete(message);
    }
}
