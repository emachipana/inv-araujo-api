package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.MessageDao;
import com.inversionesaraujo.api.model.entity.Message;
import com.inversionesaraujo.api.service.IMessage;

@Service
public class MessageImpl implements IMessage {
    @Autowired
    private MessageDao messageDao;

    @Transactional(readOnly = true)
    @Override
    public List<Message> listAll() {
        return messageDao.findAll();
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

    @Transactional(readOnly = true)
    @Override
    public boolean ifExists(Integer id) {
        return messageDao.existsById(id);
    }
}
