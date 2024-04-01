package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.Security.KeycloakConfiguration;
import com.example.GestionDesComptesClients.Security.UserMapper;
import com.example.GestionDesComptesClients.entities.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    KeycloakConfiguration keycloakConfiguration;

    @Value("${realm}")
    private String realm;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Keycloak keycloak = keycloakConfiguration.getKeycloakInstance();
        UserRepresentation userRep = userMapper.mapUserRep(user);
        Response response = keycloak.realm(realm).users().create(userRep);
        if(response.getStatus() == 201){
            String userId = keycloak.realm(realm).users().search(user.getUsername()).get(0).getId();
            assignRole(userId, user.getRealmRoles());
        }
        return ResponseEntity.ok(user);
    }
    public void assignRole(String userId, List<String> realmRoles) {
        Keycloak keycloak = keycloakConfiguration.getKeycloakInstance();
        List<RoleRepresentation> roleReps = realmRoles.stream()
                .map(roleName -> keycloak.realm(realm).roles().get(roleName).toRepresentation())
                .toList();
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(roleReps);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> UpdateUser(@PathVariable("username") String username, @RequestBody User user){
        Keycloak keycloak = keycloakConfiguration.getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().search(username);
        if(userRepresentations.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserRepresentation userUpdate = userMapper.mapUserRepToUpdate(user);
        keycloak.realm(realm).users().get(userRepresentations.get(0).getId()).update(userUpdate);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updatePassword/{username}")
    public ResponseEntity<?> updatePassword(@PathVariable("username") String username, @RequestParam("newPassword") String newPassword){
        Keycloak keycloak = keycloakConfiguration.getKeycloakInstance();
        List<UserRepresentation> users = keycloak.realm(realm).users().search(username);
        if(users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserRepresentation updatePass = userMapper.mapUserRepToUpdatePassword(newPassword);
        keycloak.realm(realm).users().get(users.get(0).getId()).update(updatePass);
        return ResponseEntity.ok(updatePass);
    }
}
