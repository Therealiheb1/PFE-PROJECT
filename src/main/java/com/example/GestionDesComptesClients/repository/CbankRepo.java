package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.Cbank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CbankRepo extends JpaRepository<Cbank, Long> {
    List<Cbank> findByClient(Long clientId);
}
