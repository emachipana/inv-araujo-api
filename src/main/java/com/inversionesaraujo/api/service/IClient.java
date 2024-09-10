package com.inversionesaraujo.api.service;

import java.util.List;

import com.inversionesaraujo.api.model.entity.Client;

public interface IClient {
    List<Client> listAll();

    Client save(Client client);

    Client findById(Integer id);

    void delete(Client client);
}
