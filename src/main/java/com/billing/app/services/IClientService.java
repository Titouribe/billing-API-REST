package com.billing.app.services;

import com.billing.app.model.entities.Client;

import java.util.List;

public interface IClientService {
    public Client saveClient(Client client);
    public List<Client> findAll();
    public Client findById(Long id);
    public List<Client> findAllByEmail(String email);
    public Client findByEmail(String email);
    public List<Client> findAllByName(String name);
    public String deleteClient(Long id);
    public String updateClient(Long id, Client client);
}
