package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class CompteEpargne extends Compte {
    public CompteEpargne(Date dateCreation, double solde, User user) {
        super(dateCreation, solde, user);
    }
}
