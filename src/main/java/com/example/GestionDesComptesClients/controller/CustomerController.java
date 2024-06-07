package com.example.GestionDesComptesClients.controller;


import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.entities.*;
import com.example.GestionDesComptesClients.service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cust")
public class CustomerController {


    @Autowired
    private CustService custService;

    @GetMapping("/all")
    public List<Customer> getAllCustomers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return custService.getAllCustomers(page, size, sortBy);
    }

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody UserCustomerDTO userDTO) {
        Customer customer = custService.mapCustomerDTOToCustomer(userDTO);
        return custService.createCustomer(customer);
    }

    @PutMapping("/{cin}")
    public Customer editCustomer(@PathVariable String cin, @RequestBody Customer updatedCustomerDetails) {
        return custService.editCustomer(cin, updatedCustomerDetails);
    }

    @GetMapping("/{cin}")
    public Customer getCustomerByCin(@PathVariable String cin) {
        return custService.getCustomerByCin(cin);
    }

    @GetMapping("/compareCin")
    public String compareCin(@RequestParam("cin") String cin) {
        return custService.compareCin(cin);
    }

    @PostMapping("/addCompte/{cin}")
    public Cbank addCompte(@PathVariable String cin) {
        return custService.addCompte(cin);
    }

    @PostMapping("/archiveAccountByRib/{rib}")
    public String archiveAccountByRib(@PathVariable String rib) {
        return custService.archiveAccountByRib(rib);
    }

    @GetMapping("/comptes")
    public List<Cbank> getAllComptes(@RequestParam long id,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "true") boolean status,
                                     @RequestParam(defaultValue = "rib") String sortBy) {
        return custService.getAllComptes(id, page, size, status, sortBy);
    }

    @PostMapping("/transfer")
    public String transferFunds(@RequestParam("senderRib") String senderRib,
                                @RequestParam("receiverRib") String receiverRib,
                                @RequestParam("value") Double value) {
        return custService.transferFunds(senderRib, receiverRib, value);
    }

    @PostMapping("/addSolde")
    public Cbank addSolde(@RequestParam("rib") String rib,
                          @RequestParam("amount") Double amount) {
        return custService.addSolde(rib, amount);
    }

    @PutMapping("/changeAccountType/{rib}")
    public String changeAccountType(@PathVariable("rib") String rib,
                                    @RequestParam("newTypeId") String newTypeId) {
        return custService.changeAccountType(rib, newTypeId);
    }
    @GetMapping("/filter/{keyword}")
    public List<Customer> filterCustomersByKeyword(@PathVariable String keyword) {
        return custService.filterCustomersByKeyword(keyword);
    }
    @PostMapping("/filter/{page}/{size}")
    public Page<Customer> filterCustomers(
            @PathVariable int page,
            @PathVariable int size,
            @RequestBody Map<String, String> params) {

        Pageable pageable = PageRequest.of(page, size);
        return custService.filterCustomers(params, pageable);
    }





}
