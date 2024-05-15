package com.example.GestionDesComptesClients.controller;


import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.repository.CbankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compte")
public class CompteController {
    @Autowired
    CbankRepo cbankRepo;
    @GetMapping("/customer/{customerId}")
    public List<Cbank> getComptesByCustomer(@PathVariable Long customerId) {
        Customer customer = new Customer();
        if (customer == null) {
            return null;
        }
        return cbankRepo.findByClient(customer);
    }
}
