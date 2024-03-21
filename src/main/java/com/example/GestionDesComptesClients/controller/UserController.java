package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.Security.KeycloakSecurityUtil;
import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.repository.UserRepo;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {
    @Autowired
    KeycloakSecurityUtil keycloakSecurityUtil;
    @Value("${realm}")
    private String realm;

    @GetMapping
    @RequestMapping("/users")
    public List<User> getAllUsers(){
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().list();
        return mapUsers(userRepresentations);
    }
    @GetMapping(value = "/users/{Id}")
    public User getUser(@PathVariable("id") String id){
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        return mapUser(keycloak.realm(realm).users().get(id).toRepresentation());
    }
    @PostMapping("/add")
    @RequestMapping("/user")
    public Response addUser(@RequestBody User user){
        UserRepresentation userRep = mapUserRep(user);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        keycloak.realm(realm).users().create(userRep);
        return Response.ok(user).build();
    }

//    @PostMapping("/users")
//    public User createUser(@RequestBody User user) {
//        return UserRepo.save(user);
//    }
//    @PostMapping("/adduser")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User newUser =
//        return new ResponseEntity<>(user);
//    }

    @PutMapping(value = "user")
    public Response UpdateUser(@RequestBody User user){
        UserRepresentation userRepresentation = mapUserRep(user);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        keycloak.realm(realm).users().get(user.getId().toString()).update(userRepresentation);
        return Response.ok(user).build();
    }
    private List<User> mapUsers(List<UserRepresentation> userRepresentations){
        List<User> users = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRepresentations)){
            userRepresentations.forEach(userRep ->
                    users.add(mapUser(userRep)));
        }
        return users;
    }
    private User mapUser(UserRepresentation userRep){
        User user = new User();
        user.setId(user.getId());
        user.setNom_prenom(user.getNom_prenom());
        user.setEmail(user.getEmail());
        return user;
    }
    private UserRepresentation mapUserRep(User user){
        UserRepresentation userRep = new UserRepresentation();
        userRep.setId(user.getId().toString());
        userRep.setUsername(user.getNom_prenom());
        userRep.setEmail(user.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(false);
        List<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();

        creds.add(cred);
        userRep.setCredentials(creds);
        return userRep;
    }
}
