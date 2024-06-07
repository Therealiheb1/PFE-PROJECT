package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TypeCompte")
public class TypeCompte {

    @Id
    private String id;
    @Column(name = "type")
    private String type;




}


