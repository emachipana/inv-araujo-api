package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Message;

public interface IMessage {
    List<Message> listAll();

    Message save(Message message);

    Message findById(Integer id);

    void delete(Message message);
}
