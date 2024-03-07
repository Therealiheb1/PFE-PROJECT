package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class CompteCourant extends Compte {
    public CompteCourant(Date dateCreation, double solde, User user) {
        super(dateCreation, solde, user);
    }
}
