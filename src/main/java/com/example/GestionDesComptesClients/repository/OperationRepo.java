package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepo extends JpaRepository<Operation, Long> {
}
