package com.billing.app.repositories;

import com.billing.app.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByFirstName(String name);
    Optional<Client> findByEmail(String name);
    List<Client> findAllByEmail(String email);
}
