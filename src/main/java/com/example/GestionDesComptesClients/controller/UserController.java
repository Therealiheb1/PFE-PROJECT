package com.example.GestionDesComptesClients.controller;

import Exceptions.UserNotFoundException;
import com.example.GestionDesComptesClients.Security.KeycloakConfiguration;
import com.example.GestionDesComptesClients.Security.UserMapper;
import com.example.GestionDesComptesClients.entities.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    KeycloakConfiguration keycloakConfiguration;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            String targetRealm = user.getTargetRealm();
            Keycloak keycloak = keycloakConfiguration.getKeycloakInstance(targetRealm);
            UserRepresentation userRep = userMapper.mapUserRep(user);
            Response response = keycloak.realm(targetRealm).users().create(userRep);
            if (response.getStatus() == 201) {
                String userId = keycloak.realm(targetRealm).users().search(user.getUsername()).get(0).getId();
                assignRole(userId, user.getRealmRoles(), targetRealm);
                return ResponseEntity.ok().body("User created successfully");
            } else {
                return ResponseEntity.status(response.getStatus()).body(response.getStatusInfo().getReasonPhrase());
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error accessing Keycloak: " + e.getMessage());
        }
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
