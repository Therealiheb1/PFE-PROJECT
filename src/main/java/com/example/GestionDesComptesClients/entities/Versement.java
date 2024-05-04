package com.example.GestionDesComptesClients.entities;


import jakarta.persistence.Entity;

import java.util.Date;

@Entity

public class Versement extends Operation {
    public Versement(Date dateOperation, double montant, Compte compte) {

        super(dateOperation, montant, compte);
    }
}
