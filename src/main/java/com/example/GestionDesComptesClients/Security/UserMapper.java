package com.example.GestionDesComptesClients.Security;

import com.example.GestionDesComptesClients.entities.User;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserMapper {
    public UserRepresentation mapUserRep(User user){
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(user.getUsername());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(false);
        List<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        userRep.setCredentials(creds);
        userRep.setRequiredActions(Collections.singletonList("UPDATE_PASSWORD"));
        return userRep;
    }

    public UserRepresentation mapUserRepToUpdate(User user) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getEmail());
        return userRep;
    }
    public UserRepresentation mapUserRepToUpdatePassword(String newPassword){
        UserRepresentation userRep = new UserRepresentation();
        List<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(newPassword);
        creds.add(cred);
        userRep.setCredentials(creds);
        return userRep;
    }

    public User mapUser(UserRepresentation userRep) {
        User user = new User();
        user.setUsername(userRep.getUsername());
        user.setFirstName(userRep.getFirstName());
        user.setLastName(userRep.getLastName());
        user.setEmail(userRep.getEmail());
        return user;
    }
}
