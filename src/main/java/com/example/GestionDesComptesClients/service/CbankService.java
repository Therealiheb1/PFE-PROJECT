package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.repository.CbankRepo;
import com.example.GestionDesComptesClients.repository.CustomerRepo;
import com.example.GestionDesComptesClients.repository.OperationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CbankService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CbankRepo cbankRepo;

    @Autowired
    private CustomerRepo custrepo;

    @Autowired
    private OperationRepository opRepo;

    public List<Cbank> getComptesByCustomer(Long customerId) {
        Customer customer = custrepo.findById(String.valueOf(customerId))
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return cbankRepo.findByClient(customer);
    }

    public List<Cbank> filterCbank(Customer customer, boolean status, String rib, String iban, Double solde) {
        String jpql = "SELECT c FROM Cbank c WHERE c.client = :customer AND c.status = :status";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer", customer);
        parameters.put("status", status);

        if (rib != null) {
            jpql += " AND c.rib = :rib";
            parameters.put("rib", rib);
        }
        if (iban != null) {
            jpql += " AND c.iban = :iban";
            parameters.put("iban", iban);
        }
        if (solde != null) {
            jpql += " AND c.solde = :solde";
            parameters.put("solde", solde);
        }

        TypedQuery<Cbank> query = entityManager.createQuery(jpql, Cbank.class);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    public List<Cbank> getAccountsByCustomerEmail(String email) {
        return cbankRepo.findByClientEmail(email);
    }

    public Double getAccountBalanceByRib(String rib) {
        Optional<Cbank> accountOptional = cbankRepo.findByRib(rib);
        if (accountOptional.isPresent()) {
            return accountOptional.get().getSolde();
        } else {
            throw new RuntimeException("Account not found for RIB: " + rib);
        }
    }

    public long countEpargneAccounts() {
        return cbankRepo.countByTypes_Type("epargne");
    }

    public long countCourantAccounts() {
        return cbankRepo.countByTypes_Type("courant");
    }

    public long countAllAccounts() {
        return cbankRepo.count();
    }

    public long countOperationsThisMonth() {
        YearMonth currentYearMonth = YearMonth.now();
        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();
        return opRepo.countByDateVBetween(firstDayOfMonth, lastDayOfMonth);
    }

}
