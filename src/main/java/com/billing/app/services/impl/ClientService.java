package com.billing.app.services.impl;

import com.billing.app.constants.Constants;
import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.exceptions.RequestException;
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
    @Autowired
    private ErrorsConstants errorsConstants;
    @Autowired
    private ValidConstants validConstants;

    @Override
    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()){
            throw new RequestException("401", errorsConstants.notFound(Constants.CLIENT, String.valueOf(id)));
        }
        return clientOptional.get();
    }

    @Override
    public Client findByEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if (clientOptional.isEmpty()){
            throw new RequestException("401", errorsConstants.notFound(Constants.CLIENT, email));
        }
        return clientOptional.get();
    }

    @Override
    public List<Client> findAllByName(String name) {
        if(clientRepository.findAllByFirstName(name).isEmpty()){
            throw new RequestException("401", errorsConstants.notFound(Constants.CLIENT, name));
        }
        return clientRepository.findAllByFirstName(name);
    }

    @Override
    @Transactional
    public String deleteClient(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new RequestException("401", errorsConstants.notFound(Constants.CLIENT, String.valueOf(id)));
        }
        clientRepository.deleteById(clientOptional.get().getId());
        return validConstants.foundAndDelete(clientOptional.get().getFirstName());
    }

    @Override
    @Transactional
    public String updateClient(Long id, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new RequestException("401", errorsConstants.notFound(Constants.CLIENT, String.valueOf(id)));
        }
        clientOptional.get().setEmail(client.getEmail());
        clientOptional.get().setFirstName(client.getFirstName());
        clientOptional.get().setLastName(client.getLastName());
        clientRepository.save(clientOptional.get());
        return validConstants.foundAndUpdated(clientOptional.get().getFirstName());
    }

}
