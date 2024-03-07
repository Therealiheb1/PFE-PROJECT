package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.repository.UserRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class Userservice {
    private final UserRepo userrepo;
@Autowired
    public Userservice(UserRepo userrepo) {
        this.userrepo = userrepo;
    }
}
