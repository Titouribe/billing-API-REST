package com.billing.app.services.impl;

import com.billing.app.model.entities.Client;
import com.billing.app.model.mappers.ClientMapper;
import com.billing.app.repositories.IClientRepository;
import com.billing.app.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private IClientRepository clientRepository;

    @Override
    @Transactional
    public Client saveClient(Client client){
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    @Override
    public Client findByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        return clientOptional.orElse(null);
    }

    @Override
    public List<Client> findAllByName(String name) {
        return clientRepository.findByFirstName(name);
    }

    @Override
    @Transactional
    public String deleteClient(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            clientRepository.delete(clientOptional.get());
            return "Client " + clientOptional.get().getFirstName() + " deleted";
        } else {
            return "Client not found";
        }
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isPresent()){
            clientOptional.get().setEmail(client.getEmail());
            clientOptional.get().setFirstName(client.getFirstName());
            clientOptional.get().setLastName(client.getLastName());
            return clientRepository.save(clientOptional.get());
        } else {
            return null;
        }
    }

}
