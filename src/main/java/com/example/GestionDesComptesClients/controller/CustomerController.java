package com.example.GestionDesComptesClients.controller;

import Exceptions.ResourceNotFoundException;
import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.entities.NumeroCompte;
import com.example.GestionDesComptesClients.repository.CbankRepo;
import com.example.GestionDesComptesClients.repository.CustomerRepo;
import com.example.GestionDesComptesClients.repository.NumeroCompteRepo;
import com.example.GestionDesComptesClients.service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cust")
public class CustomerController {

    @Autowired
    CustomerRepo custrepo;

    @Autowired
    CbankRepo cbankRepo;

    @Autowired
    NumeroCompteRepo numCompteRepo;
    @Autowired
    private CustService custService;
    // get all Customer
    @GetMapping("/Customer")
    public List<Customer> getAllCustomers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1") int size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Customer> pageResult = custrepo.findAll(pageable);

        return pageResult.getContent();
    }


    // create Customer rest api
    @PostMapping("/Customer")
    public Customer createCustomer(@RequestBody UserCustomerDTO userDTO) {
        Customer customer = mapCustomerDTOToCustomer(userDTO);
        return custService.createCustomer(customer);
    }
    private Customer mapCustomerDTOToCustomer(UserCustomerDTO userDTO) {
        Customer customer = new Customer();
        customer.setFirstName(userDTO.getFirstName());
        customer.setLastName(userDTO.getLastName());
        customer.setEmail(userDTO.getEmail());
        customer.setTel(userDTO.getTel());
        customer.setCin(userDTO.getCin());
        customer.setSexe(userDTO.getSexe());
        customer.setAgence(userDTO.getAgence());
        customer.setProfession(userDTO.getProfession());
        //String to LocalDate
        LocalDate date = LocalDate.parse(userDTO.getDateN(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        customer.setDateN(date);
        return customer;
    }
    @PutMapping("/Customer/{cin}")
    public ResponseEntity<Customer> editCustomer(@PathVariable String cin, @RequestBody Customer updatedCustomerDetails) {
        Customer existingCustomer = custrepo.findByCin(cin);

        if (existingCustomer != null) {
            // Update the existing customer with the new details
            existingCustomer.setFirstName(updatedCustomerDetails.getFirstName());
            existingCustomer.setLastName(updatedCustomerDetails.getLastName());
            existingCustomer.setCin(updatedCustomerDetails.getCin());
            existingCustomer.setSexe(updatedCustomerDetails.getSexe());
            existingCustomer.setTel(updatedCustomerDetails.getTel());
            existingCustomer.setEmail(updatedCustomerDetails.getEmail());
            existingCustomer.setAgence(updatedCustomerDetails.getAgence());
            existingCustomer.setProfession(updatedCustomerDetails.getProfession());
            existingCustomer.setDateN(updatedCustomerDetails.getDateN());

            // Save the updated customer
            Customer updatedCustomer = custrepo.save(existingCustomer);
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/Customer/{cin}")
    public ResponseEntity<Customer> getCustomerByCin(@PathVariable String cin) {
        Customer cust = custrepo.findByCin(cin);
        System.out.println(cust);
        return ResponseEntity.ok(cust);
    }

    @PostMapping("/compare")
    public ResponseEntity<String> compareCin(@RequestBody String cin) {

        Customer storedCin = custrepo.findByCin(cin);

        if (cin.equals(storedCin)) {
            return ResponseEntity.ok("CIN values match!");
        } else {
            return ResponseEntity.badRequest().body("CIN values do not match.");
        }
    }



//    @GetMapping("/Customer/{customerId}/accounts")
//    public ResponseEntity<List<Cbank>> getCustomerAccounts(@PathVariable Long customerId) {
//        // Check if the customer exists
//        Optional<Customer> optionalCustomer = custrepo.findById(customerId);
//        if (!optionalCustomer.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Fetch all accounts related to the customer
//        List<Cbank> customerAccounts = cbankRepo.findByClientId(customerId);
//
//        if (customerAccounts.isEmpty()) {
//            return ResponseEntity.noContent().build(); // No accounts found
//        } else {
//            // Filter active accounts (optional)
//            List<Cbank> activeAccounts = new ArrayList<>();
//            for (Cbank account : customerAccounts) {
//                if (account.getStatus()) {
//                    activeAccounts.add(account);
//                }
//            }
//            // Return all accounts or filtered active accounts
//            return ResponseEntity.ok(activeAccounts.isEmpty() ? customerAccounts : activeAccounts);
//        }
//    }


    ////////////////////////////////////////////////////////
    @PostMapping("/addCompte/{cin}")
    public ResponseEntity<Cbank> addCompte(@PathVariable String cin) {
        System.out.println("+++++++++++++++++++ "+cin);

        // Find the customer based on CIN.
        Customer customer = custrepo.findByCin(cin);
        if (customer == null) {

            System.out.println("+++++++++++++++++++++++++++++++++++ not found");
        }else {
            System.out.println("+++++++++++++++++++++++++++++++++++  found");
        }

        // Check if the customer already has a compte.
        List<Cbank> existingAccounts = cbankRepo.findByClient(customer);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx "+existingAccounts);
        NumeroCompte numeroCompte;

        if (existingAccounts.isEmpty()) {
            System.out.println("----------------------------- "+"compte not found");
            numeroCompte = createNumeroCompte();
            System.out.println("numeroCompte            "+numeroCompte);
        } else {
            System.out.println("----------------------------- "+"compte found");
            numeroCompte = existingAccounts.get(0).getNumCompte();
        }

        // Generate RIB and IBAN for the new compte.
        String Rib = generateRib();
        String Iban = "TN5904" + (new Random().nextInt(100) +Rib+new Random().nextInt(100));
        System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*//*"+Rib);
        System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*//*"+Iban);
        System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*//*"+numeroCompte.getNum());
        // Create the new compte.
        Cbank newCompte = Cbank.builder()
                .rib(Rib)
                .client(customer)
                .numCompte(numeroCompte)
                .iban(Iban)
                .solde(0.0)
                .status(true)
                .build();



        newCompte.getNumCompte().getNum();
        cbankRepo.save(newCompte);
        System.out.println("done ////////////////////////////////////");
        return ResponseEntity.ok(newCompte);

    }
    @GetMapping("/Rib")
        private String generateRib() {
            return String.valueOf((100000000 + new Random().nextInt(900000000)));
        }

        // Method to generate a unique IBAN.


        // Method to create a new NumeroCompte.
        private NumeroCompte createNumeroCompte() {
            NumeroCompte numeroCompte = new NumeroCompte();
            numeroCompte.setNum(String.valueOf(System.currentTimeMillis() % 10000000));
            return numCompteRepo.save(numeroCompte);
        }



//////////////////////////////////////////////////////////

    @PutMapping("/archive/{rib}/{status}")
    public ResponseEntity<String> archiveAccountByRib(@PathVariable String rib,@PathVariable boolean status) {
        System.out.println("---------------------  RIB "+ rib);
        Cbank cbank = cbankRepo.findById(rib).orElse(null);

        if (cbank!=null) {
            cbank.setStatus(status); // Set status to archived
            cbankRepo.save(cbank); // Save the updated account
            return ResponseEntity.ok("Account with RIB " + rib + " has been archived.");
        } else {
            System.out.println("Account not found with RIB: " + rib); // Print not found message
            return ResponseEntity.notFound().build(); // Account not found
        }
    }


}


