package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "compte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Compte implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "code_compte", nullable = false, updatable = false)
    private Long codeCompte;
    @Column(name = "date_creation")
    private Date dateCreation;
    @Column(name = "solde")
    private double solde;
    @ManyToOne
    @JoinColumn
    private User user;

    public Compte(Date dateCreation, double solde, User user) {
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.user = user;
    }
}
