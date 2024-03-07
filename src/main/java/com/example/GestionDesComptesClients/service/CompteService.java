package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.Compte;
import com.example.GestionDesComptesClients.repository.CompteRepo;
import org.springframework.stereotype.Service;

@Service
public class CompteService {
    private final CompteRepo compterepo;

    public CompteService(CompteRepo compterepo) {
        this.compterepo = compterepo;
    }
    public Compte addcompte(Compte compte) {
        return compterepo.save(compte);
    }

    public Compte updateCompte(Compte compte) {
        return compterepo.save(compte);
    }
}

