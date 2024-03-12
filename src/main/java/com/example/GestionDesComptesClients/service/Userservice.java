package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service
public class Userservice {

    private final UserRepo userrepo;
    @Autowired
    public Userservice(UserRepo userrepo) {
        this.userrepo = userrepo;
    }
    public static User getLoggedUser() {


        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String email = String.valueOf(token.getTokenAttributes().get("email"));
        User user = UserRepo.findByEmail(email);

        return user;
    }

    public void syncUser(User user) {
        if (user == null) {
            throw new EntityNotFoundException("Error sync");
        }

        User saveUser = user;
        Optional<User> optionalUser = Optional.ofNullable(UserRepo.findByEmail(user.getEmail()));

        if (optionalUser.isPresent()) {
            saveUser = optionalUser.get();
            saveUser.setNom(user.getNom());
            saveUser.setPrenom(user.getPrenom());
        }

        UserRepo.save(saveUser);
    }



}
