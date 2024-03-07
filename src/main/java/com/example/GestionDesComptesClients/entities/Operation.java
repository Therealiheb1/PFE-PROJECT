package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "operation")
public abstract class Operation implements Serializable {
    @Id
    @GeneratedValue
    @Column(name ="num_operation" )
    private Long num;
    @Column(name = "date_operation")
    private Date dateOperation;
    @Column(name = "montant")
    private double montant;
    @ManyToOne
    @JoinColumn
    private Compte compte;

    public Operation(Date dateOperation, double montant, Compte compte) {
        this.dateOperation = dateOperation;
        this.montant = montant;
        this.compte = compte;
    }
}
