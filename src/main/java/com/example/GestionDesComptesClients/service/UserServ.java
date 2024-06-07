package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.Security.CustomerMapper;
import com.example.GestionDesComptesClients.Security.KeycloakConfig;
import com.example.GestionDesComptesClients.Security.UserMapper;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.entities.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServ {

    @Autowired
    private final MailSservice mailService;
    @Autowired
    private final CustService custService;
    @Autowired
    private final CustomerMapper customerMapper;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final KeycloakConfig keycloakConfig;

    @Value("${realm}")
    private String realm;

    public ResponseEntity<?> createUser(UserCustomerDTO userDTO) {
        try {
            UserRepresentation userRep = customerMapper.mapUserDTOToUserRep(userDTO);
            String generatedUsername = customerMapper.generateRandomUsername(8);
            userRep.setUsername(generatedUsername);
            userRep.setCredentials(Collections.singletonList(createPasswordCredential()));

            Keycloak keycloak = keycloakConfig.getKeycloakInstance();
            String connectedUserId = getConnectedUserId();
            List<String> connectedUserRoles = keycloak.realm(realm).users().get(connectedUserId).roles().realmLevel().listAll().stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());

            String roleToAssign = determineRoleToAssign(connectedUserRoles);

            List<UserRepresentation> existingUsers = keycloak.realm(realm).users().search(generatedUsername, true);
            if (!existingUsers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with the username already exists.");
            }

            Response response = keycloak.realm(realm).users().create(userRep);

            if (response.getStatus() == 201) {
                String userId = keycloak.realm(realm).users().search(generatedUsername).get(0).getId();
                Customer customer = customerMapper.mapCustomerDTOToCustomer(userDTO);
                if (customer.getDateN() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date of birth (daten) is required.");
                }
                customer.setKeyId(userId);
                custService.createCustomer(customer);

                assignRole(userId, Collections.singletonList(roleToAssign));
                String to = userDTO.getEmail();
                send(to, "Bienvenue à votre nouveau compte", "Bonjour,\n" +
                        "Votre compte a été créé avec succès. \n" +
                        "Nom d'utilisateur: " + generatedUsername + "\n" +
                        "Mot de passe: 123\n" +
                        "Vous pouvez vous connecter à votre compte en utilisant le lien suivant : http://localhost:4200\n" +
                        "Cordialement,\n" +
                        "L'équipe de support");
                return ResponseEntity.ok().body("User created successfully. Username: " + generatedUsername +
                        ". Customer created successfully with Keycloak ID: " + userId + ".");
            } else {
                return ResponseEntity.status(response.getStatus()).body(response.getStatusInfo().getReasonPhrase());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    public Response updateUser(String username, User user) {
        Keycloak keycloak = keycloakConfig.getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().search(username);
        if (userRepresentations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        UserRepresentation userUpdate = userMapper.mapUserRepToUpdate(user);
        keycloak.realm(realm).users().get(userRepresentations.get(0).getId()).update(userUpdate);
        return Response.ok(user).build();
    }

    public Response updatePassword(String username, String newPassword) {
        Keycloak keycloak = keycloakConfig.getKeycloakInstance();
        List<UserRepresentation> users = keycloak.realm(realm).users().search(username);
        if (users.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        UserRepresentation updatePass = userMapper.mapUserRepToUpdatePassword(newPassword);
        keycloak.realm(realm).users().get(users.get(0).getId()).update(updatePass);
        return Response.ok(updatePass).build();
    }

    private String getConnectedUserId() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getToken().getSubject();
    }

    private String determineRoleToAssign(List<String> connectedUserRoles) {
        System.out.println("determineRoleToAssign       ");
        if (connectedUserRoles.contains("ROLE_superAdmin")) {
            return "ROLE_admin";
        } else if (connectedUserRoles.contains("ROLE_admin")) {
            return "ROLE_user";
        } else {
            throw new IllegalArgumentException("Connected user does not have the required roles.");
        }
    }

    private void assignRole(String userId, List<String> realmRoles) {
        Keycloak keycloak = keycloakConfig.getKeycloakInstance();
        List<RoleRepresentation> roleReps = realmRoles.stream()
                .map(roleName -> keycloak.realm(realm).roles().get(roleName).toRepresentation())
                .collect(Collectors.toList());
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(roleReps);
    }

    private CredentialRepresentation createPasswordCredential() {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue("123");
        return credential;
    }

    public String send(String to, String subject, String text) {
        try {
            mailService.sendEmail(to, subject, text);
            return "Your mail has been sent to the user.";
        } catch (MailException | MessagingException e) {
            return "Error sending email: " + e.getMessage();
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
