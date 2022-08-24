package com.billing.app.services.impl;

import com.billing.app.constants.ErrorsConstants;
import com.billing.app.constants.ValidConstants;
import com.billing.app.exceptions.RequestException;
import com.billing.app.model.entities.Client;
import com.billing.app.repositories.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientServiceTest {

    @MockBean
    private IClientRepository clientRepository;

    @MockBean
    private ValidConstants validConstants;

    @MockBean
    private ErrorsConstants errorsConstants;

    @Autowired
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
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        assertEquals(clientService.findById(anyLong()).getFirstName(), client.getFirstName());
    }

    @Test
    void testFindByIdWhenClientDontExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            clientService.findById(2L);
        });
        verify(clientRepository).findById(anyLong());
    }

    @Test
    void testFindAllByNameReturnEmptyList() {
        when(clientRepository.findAllByFirstName(any())).thenReturn(List.of());
        assertThrows(RequestException.class, () -> {
            clientService.findAllByName("test");
        });
    }

    @Test
    void testFindAllByNameReturnList() {
        when(clientRepository.findAllByFirstName(any())).thenReturn(List.of(client));
        assertFalse(clientService.findAllByName(any()).isEmpty());
    }

    @Test
    void findByEmailWhenClientExist() {
        when(clientRepository.findByEmail(any())).thenReturn(Optional.of(client));
        assertEquals(clientService.findByEmail("test@email.com").getId(), client.getId());
    }

    @Test
    void findByEmailWhenClientDontExist() {
        when(clientRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            clientService.findByEmail("test");
        });
        verify(clientRepository).findByEmail("test");
    }

    @Test
    void deleteClientWhenClientExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        assertEquals(clientService.deleteClient(1L), validConstants.foundAndDelete(client.getFirstName()));
    }

    @Test
    void deleteClientWhenClientDontExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            clientService.deleteClient(2L);
        });
        verify(clientRepository, never()).deleteById(2L);
    }

    @Test
    void updateClientWhenClientExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        Client clientOne = clientRepository.findById(any()).get();
        clientOne.setEmail("testupdate@test.com");
        clientOne.setFirstName("userupdate");
        clientOne.setLastName("testupdate");
        clientRepository.save(clientOne);
        assertEquals(clientService.updateClient(1L, clientOne), validConstants.foundAndUpdated(clientOne.getFirstName()));
        assertEquals(clientOne.getFirstName(), client.getFirstName());
    }

    @Test
    void updateClientWhenClientDontExist() {
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(RequestException.class, () -> {
            clientService.updateClient(2L, client);
        });
        verify(clientRepository, never()).save(client);
    }
}
