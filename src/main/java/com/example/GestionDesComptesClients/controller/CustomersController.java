package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.customers;
import com.example.GestionDesComptesClients.entities.num_compte;
import com.example.GestionDesComptesClients.repository.CbankRepo;
import com.example.GestionDesComptesClients.repository.CustomersRepo;
import com.example.GestionDesComptesClients.repository.NumCompteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cust")
public class CustomersController {
    @Autowired
    CustomersRepo custrepo;
    @Autowired
    CbankRepo cbankRepo;

    @Autowired
    NumCompteRepo numCompteRepo;


    // get all customers
    @GetMapping("/customers")
    public List<customers> getAllcustomers() {
        return custrepo.findAll();
    }

    // create customers rest api
    @PostMapping("/customers")
    public customers createCustomer(@RequestBody customers cust) {
        // Check if the provided cin exists in the database
        customers existingCustomer = custrepo.findBycin(cust.getCin());

        if (existingCustomer != null) {
            String first8Characters = existingCustomer.getCin().substring(0, 8);

            if (existingCustomer.getCin().length() == 8) {

                LocalDate birthday = cust.getDaten();
                LocalDate currentDate = LocalDate.now();
                Period period = Period.between(birthday, currentDate);
                int age = period.getYears();

                if (age < 18) {
                    char lastChar = existingCustomer.getCin().charAt(7);
                    int increment = Character.getNumericValue(lastChar) + 1;
                    String newCin = first8Characters + increment;
                    existingCustomer.setCin(newCin);
                }
            } else if (existingCustomer.getCin().length() != 8) {
                int x = Integer.parseInt(existingCustomer.getCin().substring(8));
                x++;
                String newCin = first8Characters + x;
                existingCustomer.setCin(newCin);
            }
            return custrepo.save(existingCustomer);
        } else {
            return custrepo.save(cust);
        }
    }

    @GetMapping("/customers/{cin}")
    public ResponseEntity<customers> getCustomersByCin(@PathVariable String cin) {
        customers cust = custrepo.findBycin(cin);
        System.out.println(cust);
        return ResponseEntity.ok(cust);
    }

    @PostMapping("/compare")
    public ResponseEntity<String> compareCin(@RequestBody String cin) {

        customers storedCin = custrepo.findBycin(cin);

        if (cin.equals(storedCin)) {
            return ResponseEntity.ok("CIN values match!");
        } else {
            return ResponseEntity.badRequest().body("CIN values do not match.");
        }
    }

    @PostMapping("/customers/{customerId}/accounts")
    public ResponseEntity<String> createAccountForCustomer(
            @PathVariable Long customerId) {

        customers customer = custrepo.findById(customerId).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the customer already has an account
        List<Cbank> customerAccounts = cbankRepo.findByClient(customerId);
        Long numCompte;
        if (customerAccounts.isEmpty()) {
            // If no account exists, generate a new num_compte
            numCompte = generateNumCompte();
            num_compte newAccount = num_compte.builder()

                    .num(numCompte)

                    .build();
        } else {
            // Use the num_compte of the first account
            numCompte = Long.valueOf(customerAccounts.get(0).getNum_compte());
        }
        String newIban = generateUniqueIBAN();
        // Create a new Cbank entity with provided details
        Cbank newAccount = Cbank.builder()
                .iban(newIban)
                .num_compte(Math.toIntExact(numCompte))
                .client(Math.toIntExact(customerId))
                .solde(0.0)
                .statue(true)
                .build();

        // Save the new account
        cbankRepo.save(newAccount);

        return ResponseEntity.ok("Account created successfully");
    }

    private String generateUniqueIBAN() {
        return "Generated_IBAN";
    }

    private Long generateNumCompte() {
        num_compte newNumCompte = new num_compte();
        newNumCompte.setNum(generateRandomNum()); // Set a non-null value for the "num" attribute
        numCompteRepo.save(newNumCompte);
        return newNumCompte.getNum();
    }

    // Method to generate a new num_compte
    private Long generateRandomNum() {
        return new Random().nextLong();
    }


    @GetMapping("/customers/{customerId}/accounts")
    public ResponseEntity<List<Cbank>> getCustomerAccounts(@PathVariable Long customerId) {
        // Check if the customer exists
        Optional<customers> optionalCustomer = custrepo.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Fetch all accounts related to the customer
        List<Cbank> customerAccounts = cbankRepo.findByClient(customerId);

        if (customerAccounts.isEmpty()) {
            return ResponseEntity.noContent().build(); // No accounts found
        } else {
            return ResponseEntity.ok(customerAccounts); // Return the list of accounts
        }
    }
}
