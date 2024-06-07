package com.example.GestionDesComptesClients.Security;

import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.entities.Customer;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class CustomerMapper {

    public Customer mapCustomerDTOToCustomer(UserCustomerDTO userDTO) {
        Customer customer = new Customer();
        customer.setFirstName(userDTO.getFirstName());
        customer.setLastName(userDTO.getLastName());
        customer.setEmail(userDTO.getEmail());
        customer.setTel(userDTO.getTel());
        customer.setCin(userDTO.getCin());
        customer.setSexe(userDTO.getSexe());
        customer.setAgence(userDTO.getAgence());
        customer.setProfession(userDTO.getProfession());

        // String to LocalDate with null check
        if (userDTO.getDateN() != null && !userDTO.getDateN().isEmpty()) {
            LocalDate date = LocalDate.parse(userDTO.getDateN(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            customer.setDateN(date);
        } else {
            customer.setDateN(null); // or handle appropriately
        }
        return customer;
    }

    public UserRepresentation mapUserDTOToUserRep(UserCustomerDTO userDTO) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setFirstName(userDTO.getFirstName());
        userRep.setLastName(userDTO.getLastName());
        userRep.setEmail(userDTO.getEmail());
        userRep.setUsername(userDTO.getUsername());
        userRep.setEnabled(true);
        return userRep;
    }

    public String generateRandomUsername(int length) {
        String allowedChars = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return sb.toString();
    }
}
