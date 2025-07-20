package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inversionesaraujo.api.business.dto.ClientDTO;
import com.inversionesaraujo.api.business.service.IClient;
import com.inversionesaraujo.api.model.Client;
import com.inversionesaraujo.api.model.SortBy;
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.ClientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ClientImpl implements IClient {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired 
    private ClientRepository clientRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<ClientDTO> filterClients(Integer page, Integer size, SortDirection direction, SortBy sortby) {
        Pageable pageable;
        if(sortby != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), sortby.toString());
            pageable = PageRequest.of(page, size, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }

        Page<Client> clients = clientRepo.findAll(pageable);
        return ClientDTO.toPageableDTO(clients);
    }

    @Transactional
    @Override
    public ClientDTO save(ClientDTO client) {
        Client clientSaved = clientRepo.save(ClientDTO.toEntity(client, entityManager)); 

        return ClientDTO.toDTO(clientSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDTO findById(Long id) {
        Client client = clientRepo.findById(id).orElseThrow(() -> new DataAccessException("El client no existe") {});

        return ClientDTO.toDTO(client);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        clientRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClientDTO> search(String document, String rsocial, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Client> clients = clientRepo.findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCase(
            rsocial, document, pageable
        );

        return ClientDTO.toPageableDTO(clients);
    }
}
