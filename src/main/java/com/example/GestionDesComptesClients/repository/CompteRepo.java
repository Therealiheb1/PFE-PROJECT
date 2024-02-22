package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepo extends JpaRepository<Compte, String> {
}
