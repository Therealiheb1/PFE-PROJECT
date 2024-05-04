package com.example.GestionDesComptesClients.entities;


import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Retrait extends Operation {

    public Retrait(Date dateOperation, double montant, Compte compte) {

        super(dateOperation, montant, compte);
    }
}
