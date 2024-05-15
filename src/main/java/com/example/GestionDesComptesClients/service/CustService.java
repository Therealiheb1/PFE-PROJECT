package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class CustService {
    @Autowired
    private CustomerRepo custRepo;


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
        ///String to LocalDate
        LocalDate date = LocalDate.parse(userDTO.getDateN(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        customer.setDateN(date);
        return customer;
    }


    public Customer createCustomer(Customer cust) {
       // Customer cust = mapCustomerDTOToCustomer(userDTO);
        Customer existingCustomer = custRepo.findByCin(cust.getCin());

        if (existingCustomer != null) {
            String first8Characters = existingCustomer.getCin().substring(0, 8);

            if (existingCustomer.getCin().length() == 8) {
                LocalDate birthday = cust.getDateN();
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
            return custRepo.save(existingCustomer);
        } else {
            return custRepo.save(cust);
        }
    }



}
