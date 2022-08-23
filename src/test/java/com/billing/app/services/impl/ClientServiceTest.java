package com.billing.app.services.impl;

import com.billing.app.constants.Constants;
import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.model.entities.Client;
import com.billing.app.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ValidConstants validConstants;

    @Mock
    private ErrorsConstants errorsConstants;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setEmail("test@email.com");
        client.setFirstName("user");
        client.setLastName("test");
        client.setDateCreated(new Date());
    }

    @Test
    void testSaveClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        assertEquals(client, clientService.saveClient(client));
    }

    @Test
    void testFindByIdWhenClientExist() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        assertEquals(clientService.findById(1L).getFirstName(), client.getFirstName());
    }

    @Test
    void testFindByIdWhenClientDontExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertNull(clientService.findById(2L));
    }

    @Test
    void findByEmailWhenClientExist() {
        when(clientRepository.findByEmail(any())).thenReturn(Optional.of(client));
        assertEquals(clientService.findByEmail("test@email.com").getId(), client.getId());
    }

    @Test
    void deleteClientWhenClientExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        assertEquals(clientService.deleteClient(1L), validConstants.foundAndDelete(client.getFirstName()));
    }

    @Test
    void deleteClientWhenClientDontExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertEquals(clientService.deleteClient(2L), errorsConstants.notFound(Constants.CLIENT));
    }

    @Test
    void updateClientWhenClientExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        Client clientOne = clientRepository.findById(any()).get();
        clientOne.setEmail("testupdate@test.com");
        clientOne.setFirstName("userupdate");
        clientOne.setLastName("testupdate");
        clientRepository.save(clientOne);
        assertEquals(clientService.updateClient(1L,clientOne), validConstants.foundAndUpdated(clientOne.getFirstName()));
    }

    @Test
    void updateClientWhenClientDontExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertEquals(clientService.updateClient(2L,client), errorsConstants.notFound(Constants.CLIENT));
    }
}
