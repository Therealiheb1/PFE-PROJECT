package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TypeCompteRepo extends JpaRepository<TypeCompte, String> {

}