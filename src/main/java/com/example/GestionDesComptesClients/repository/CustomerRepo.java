package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, String>, ListPagingAndSortingRepository<Customer, String> {
    Customer findByCin(String cin);
    Customer findByEmail(String email);
    Page<Customer> findAll(Pageable pageable);
    Optional<Customer> findByKeyId(String keyId);
}
