package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.service.CbankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/compte")
public class CompteController {

    @Autowired
    private CbankService service;

    @GetMapping("/customer/{customerId}")
    public List<Cbank> getComptesByCustomer(@PathVariable Long customerId) {
        return service.getComptesByCustomer(customerId);
    }

    @GetMapping("/filterCompte/{customerId}/{status}")
    public List<Cbank> filterCbankForCustomer(@PathVariable("customerId") Long customerId,
                                              @PathVariable("status") boolean status,
                                              @RequestParam(required = false) String rib,
                                              @RequestParam(required = false) String iban,
                                              @RequestParam(required = false) Double solde) {
        Customer customer = service.getComptesByCustomer(customerId).get(0).getClient(); // Assuming customer exists
        return service.filterCbank(customer, status, rib, iban, solde);
    }

    @GetMapping("/userAccounts")
    public ResponseEntity<?> getUserAccounts(Authentication authentication) {
        String email;
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtToken.getToken();
            email = jwt.getClaimAsString("email");
        } else {
            throw new IllegalStateException("Unexpected authentication type: " + authentication.getClass());
        }

        List<Cbank> userAccounts = service.getAccountsByCustomerEmail(email);
        return ResponseEntity.ok(userAccounts);
    }

    @GetMapping("/balance/{rib}")
    public ResponseEntity<Double> getAccountBalance(@PathVariable String rib) {
        Double balance = service.getAccountBalanceByRib(rib);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/countEpargneAccounts")
    public ResponseEntity<Long> countEpargneAccounts() {
        long count = service.countEpargneAccounts();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countCourantAccounts")
    public ResponseEntity<Long> countCourantAccounts() {
        long count = service.countCourantAccounts();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countAllAccounts")
    public ResponseEntity<Long> countAllAccounts() {
        long count = service.countAllAccounts();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/countOperationsThisMonth")
    public ResponseEntity<Long> countOperationsThisMonth() {
        long count = service.countOperationsThisMonth();
        return ResponseEntity.ok(count);
    }

}
