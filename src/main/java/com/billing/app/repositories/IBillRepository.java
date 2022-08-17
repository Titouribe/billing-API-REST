package com.billing.app.repositories;

import com.billing.app.model.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBillRepository extends JpaRepository<Bill, Long> {
}

