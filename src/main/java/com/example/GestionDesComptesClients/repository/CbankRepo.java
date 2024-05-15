package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface CbankRepo extends JpaRepository<Cbank, String> {

    //Cbank findByRib(Long rib);
    List<Cbank> findByClient(Customer client);

}