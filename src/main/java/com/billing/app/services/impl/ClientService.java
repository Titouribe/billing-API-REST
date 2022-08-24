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
        return clientRepository.findById(id)
                .orElseThrow(() -> new RequestException("400", errorsConstants
                        .notFound(Constants.CLIENT, String.valueOf(id))));
    }

    @Override
    public List<Client> findAllByEmail(String email) {
        return clientRepository.findAllByEmail(email)
                .orElseThrow(() -> new RequestException("400", errorsConstants.notFound(Constants.CLIENT, email)));
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RequestException("400", errorsConstants.notFound(Constants.CLIENT, email)));
    }

    @Override
    public List<Client> findAllByName(String name) {
        return clientRepository.findAllByFirstName(name)
                .orElseThrow(() -> new RequestException("400", errorsConstants.notFound(Constants.CLIENT, name)));
    }

    @Override
    @Transactional
    public String deleteClient(Long id) {
        Client clientToDelete = clientRepository.findById(id)
                .orElseThrow(() -> new RequestException("400", errorsConstants
                        .notFound(Constants.CLIENT, String.valueOf(id))));
        clientRepository.deleteById(clientToDelete.getId());
        return validConstants.foundAndDelete(clientToDelete.getFirstName());
    }

    @Override
    @Transactional
    public String updateClient(Long id, Client client) {
        Client clientToUpdate = clientRepository.findById(id)
                .orElseThrow(() -> new RequestException("400", errorsConstants
                        .notFound(Constants.CLIENT, String.valueOf(id))));
        clientToUpdate.setEmail(client.getEmail());
        clientToUpdate.setFirstName(client.getFirstName());
        clientToUpdate.setLastName(client.getLastName());
        clientRepository.save(clientToUpdate);
        return validConstants.foundAndUpdated(clientToUpdate.getFirstName());
    }

}
