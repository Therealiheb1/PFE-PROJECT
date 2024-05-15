package com.example.GestionDesComptesClients.controller;

import Exceptions.UserNotFoundException;
import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.Security.KeycloakConfiguration;
import com.example.GestionDesComptesClients.Security.UserMapper;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.repository.CbankRepo;
import com.example.GestionDesComptesClients.repository.CustomerRepo;
import com.example.GestionDesComptesClients.repository.NumeroCompteRepo;
import com.example.GestionDesComptesClients.service.CustService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakAuthenticationException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    CustomerRepo custrepo;

    @Autowired
    CbankRepo cbankRepo;

    @Autowired
    NumeroCompteRepo numCompteRepo;
    @Autowired
    private CustService custService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    KeycloakConfiguration keycloakConfiguration;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody UserCustomerDTO userDTO) {
        try {
            User user = mapUserDTOToUser(userDTO);

            String generatedUsername = generateRandomUsername(8);

            user.setUsername(generatedUsername);
            user.setPassword("123");

            String targetRealm = user.getTargetRealm();
            Keycloak keycloak = keycloakConfiguration.getKeycloakInstance(targetRealm);
            UserRepresentation userRep = userMapper.mapUserRep(user);
            Response response = keycloak.realm(targetRealm).users().create(userRep);

            if (response.getStatus() == 201) {

                Customer customer = mapCustomerDTOToCustomer(userDTO);
                 custService.createCustomer(customer);

                String userId = keycloak.realm(targetRealm).users().search(generatedUsername).get(0).getId();
                assignRole(userId, user.getRealmRoles(), targetRealm);

                return ResponseEntity.ok().body("User created successfully. Username: " + generatedUsername +
                        ". Customer created successfully.");
            } else {
                // Error occurred while creating user in Keycloak
                return ResponseEntity.status(response.getStatus()).body(response.getStatusInfo().getReasonPhrase());
            }
        } catch (IOException | KeycloakAuthenticationException e) {
            // Error occurred during Keycloak operation or user creation in the database
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

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
    private User mapUserDTOToUser(UserCustomerDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRealmRoles(Collections.singletonList(userDTO.getRealmRoles().toString()));
        return user;
    }


    private String generateRandomUsername(int length) {
        // Define characters allowed in the username
        String allowedChars = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return sb.toString();
    }

    private void assignRole(String userId, List<String> realmRoles, String targetRealm) throws IOException {
        Keycloak keycloak = keycloakConfiguration.getKeycloakInstance(targetRealm);
        List<RoleRepresentation> roleReps = realmRoles.stream()
                .map(roleName -> keycloak.realm(targetRealm).roles().get(roleName).toRepresentation())
                .collect(Collectors.toList());
        keycloak.realm(targetRealm).users().get(userId).roles().realmLevel().add(roleReps);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody User user) {
        try {
            String targetRealm = user.getTargetRealm();
            Keycloak keycloak = keycloakConfiguration.getKeycloakInstance(targetRealm);
            List<UserRepresentation> userRepresentations = keycloak.realm(targetRealm).users().search(username);
            if (userRepresentations.isEmpty()) {
                throw new UserNotFoundException("User not found with username: " + username);
            }
            UserRepresentation userUpdate = userMapper.mapUserRepToUpdate(user);
            keycloak.realm(targetRealm).users().get(userRepresentations.get(0).getId()).update(userUpdate);
            return ResponseEntity.ok().body("User updated successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error accessing Keycloak: " + e.getMessage());
        }
    }

    @GetMapping("/getUsers")
    public ResponseEntity<String> getUsers() {
        try {
            List<User> allUsers = new ArrayList<>();
            for (String realmName : keycloakConfiguration.getRealmNames()) {
                Keycloak keycloak = keycloakConfiguration.getKeycloakInstance(realmName);
                List<UserRepresentation> userReps = keycloak.realm(realmName).users().list();
                List<User> users = userReps.stream().map(userMapper::mapUser).collect(Collectors.toList());
                allUsers.addAll(users);
            }
            return ResponseEntity.ok(String.valueOf(allUsers));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error fetching users: " + e.getMessage());
        }
    }
}

//    @PutMapping("/updatePassword/{username}")
//    public ResponseEntity<?> updatePassword(@PathVariable("username") String username, @RequestParam("newPassword") String newPassword) throws IOException {
//        String targetRealm = getUserRealm(username); // Assuming a method to retrieve targetRealm based on username
//
//        // Validate targetRealm (optional: ensure it's a valid realm)
//
//        Keycloak keycloak = keycloakConfiguration.getKeycloakInstance(targetRealm);
//        List<UserRepresentation> users = keycloak.realm(targetRealm).users().search(username);
//        if (users.isEmpty()) {
//            throw new UserNotFoundException("User not found");
//        }
//        UserRepresentation updatePass = userMapper.mapUserRepToUpdatePassword(newPassword);
//        keycloak.realm(targetRealm).users().get(users.get(0).getId()).update(updatePass);
//        return ResponseEntity.ok(updatePass);
//    }
