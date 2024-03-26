package com.example.GestionDesComptesClients.service.Impl;

import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.repository.UserRepo;
import com.example.GestionDesComptesClients.service.Userservice;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements Userservice {

    @Autowired
    private UserRepo userrepo;

    @Override
    public User getLoggedUser() {

        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String email = String.valueOf(token.getTokenAttributes().get("email"));
        System.out.println("emaill      is ::   " + String.valueOf(token.getTokenAttributes()));
        return  null;
        //return userrepo.findByEmail(email);
    }

    @Override
    public void syncUser(User user) {
        if (user == null) {
            throw new EntityNotFoundException("Error sync");
        }

        User saveUser = user;
        User optionalUser = userrepo.findByEmail(user.getEmail()) ;

        if (optionalUser != null) {
            saveUser = optionalUser;
            saveUser.setUsername(user.getUsername());

        }

        userrepo.save(saveUser);
    }

}


