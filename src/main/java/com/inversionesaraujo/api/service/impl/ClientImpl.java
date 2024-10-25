package com.inversionesaraujo.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.model.dao.ClientDao;
import com.inversionesaraujo.api.model.entity.Client;
import com.inversionesaraujo.api.service.IClient;

@Service
public class ClientImpl implements IClient {
    @Autowired
    private ClientDao clientDao;

    @Transactional(readOnly = true)
    @Override
    public List<Client> listAll() {
        return clientDao.findAll();
    }

    @Transactional
    @Override
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Integer id) {
        return clientDao.findById(id).orElseThrow(() -> new DataAccessException("El client no existe") {});
    }

    @Transactional
    @Override
    public void delete(Client client) {
        clientDao.delete(client);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> search(String document, String rsocial) {
        return clientDao.findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCase(rsocial, document);
    }
}
