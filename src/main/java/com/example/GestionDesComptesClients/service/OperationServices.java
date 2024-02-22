package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.Compte;

public interface OperationServices {
    public Compte consulterCompte(String CodeCompte);
    public void verser(String CodeCompte, double montant);
    public void retirer(String CodeCompte, double montant);
    public void virement(String CodeCompte1, String CodeCompte2, double montant);
}
