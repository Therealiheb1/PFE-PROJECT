package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CbankRepo extends JpaRepository<Cbank, String> {

    //Cbank findByRib(Long rib);
    List<Cbank> findByClient(Customer client);
    List<Cbank> findByClientEmail(String email);
    long countByTypes_Type(String type);

    Optional<Cbank> findByRib(String Rib);
    Page<Cbank> findAll(Pageable pageable);
    String getCustomerEmailByRib(String rib);
    Double findSoldeByRib(String rib);
    Page<Cbank> findByClientIdAndStatus(Long clientId, Boolean status, Pageable pageable);
}