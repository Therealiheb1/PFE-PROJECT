package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.Optional;


public interface Userservice {

    public User getLoggedUser() ;

    public void syncUser(User user) ;
}
