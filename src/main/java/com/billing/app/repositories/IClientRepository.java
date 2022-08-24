package com.billing.app.repositories;

import com.billing.app.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long> {
    Optional<List<Client>> findAllByFirstName(String name);
    Optional<Client> findByEmail(String name);
    Optional<List<Client>> findAllByEmail(String email);
}
