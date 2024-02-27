package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.Compte;

public interface OperationServices {
     Compte consulterCompte(String CodeCompte);
     void verser(String CodeCompte, double montant);
     void retirer(String CodeCompte, double montant);
     void virement(String CodeCompte1, String CodeCompte2, double montant);
}
