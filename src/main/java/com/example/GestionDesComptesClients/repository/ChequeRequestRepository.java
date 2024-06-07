package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.ChequeRequest;
import com.example.GestionDesComptesClients.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChequeRequestRepository extends JpaRepository<ChequeRequest, Long> {
    List<ChequeRequest> findByStatus(ChequeRequestStatus status);
    List<ChequeRequest> findByCurrentStep(int step);
    List<ChequeRequest> findAllByCurrentStep(int step);
    List<ChequeRequest> findAllByCustomer_IdAndStatus ( Long id,Boolean status);
    Optional<ChequeRequest> findByCustomer_Id(Long id);
}
