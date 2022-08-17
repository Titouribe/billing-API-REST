package com.billing.app.repositories;

import com.billing.app.model.entities.BillLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBillLineRepository extends JpaRepository<BillLine, Long> {
}

