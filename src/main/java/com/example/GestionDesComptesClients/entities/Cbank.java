package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


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

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "num_compte", referencedColumnName = "num")
    private NumeroCompte numCompte;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    private Customer client;

    @Column(name = "solde")
    private Double solde;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "type")
    private List<TypeCompte> types = new ArrayList<>();

    @Column(name = "statue")
    private Boolean status;


}
