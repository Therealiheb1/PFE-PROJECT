package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Data
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
    private Cbank compte;

    public Operation(Date dateOperation, double montant, Cbank compte) {
        this.dateOperation = dateOperation;
        this.montant = montant;
        this.compte = compte;
    }
}
