package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.NumeroCompte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumeroCompteRepo extends JpaRepository<NumeroCompte, String> {
}