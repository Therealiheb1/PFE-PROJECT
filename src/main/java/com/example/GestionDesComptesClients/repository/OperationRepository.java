package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.Operation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    long countByDateVBetween(LocalDate startDate, LocalDate endDate);
}
