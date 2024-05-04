package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.num_compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumCompteRepo extends JpaRepository<num_compte, Long> {

}
