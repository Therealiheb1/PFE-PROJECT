package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public abstract class Compte implements Serializable {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long codeCompte;
    private Date dateCreation;
    private double solde;
    private User user;

    public Compte(Date dateCreation, double solde, User user) {
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.user = user;
    }

    public Long getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(Long codeCompte) {
        this.codeCompte = codeCompte;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
