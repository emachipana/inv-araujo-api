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
import com.inversionesaraujo.api.model.SortDirection;
import com.inversionesaraujo.api.repository.ClientRepository;

@Service
public class ClientImpl implements IClient {
    @Autowired
    private ClientRepository clientRepo;

    @Transactional(readOnly = true)
    @Override
    public Page<ClientDTO> filterClients(Integer page, Integer size, SortDirection direction) {
        Pageable pageable;
        if(direction != null) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction.toString()), "consumption");
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
        Client clientSaved = clientRepo.save(ClientDTO.toEntity(client)); 

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
    public Page<ClientDTO> search(String document, String rsocial, String city, String department, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Client> clients = clientRepo.findByRsocialContainingIgnoreCaseOrDocumentContainingIgnoreCaseOrCityContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            rsocial, document, city, department, pageable
        );

        return ClientDTO.toPageableDTO(clients);
    }
}
