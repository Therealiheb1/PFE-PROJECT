package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepo extends JpaRepository<customers, Long> {
    customers findBycin(String cin);

}