package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "compte", uniqueConstraints = {@UniqueConstraint(columnNames = {"iban"})})
public class Cbank {

    @Id
    private String rib;

    @Column(name = "iban", unique = true, nullable = false)
    private String iban;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "num_compte", referencedColumnName = "num")
    private NumeroCompte numCompte;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    private Customer client;

    @Column(name = "solde")
    private Double solde;

    @Column(name = "statue")
    private Boolean status;

}
