package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "num_compte")
public class NumeroCompte {
    @Id
    private String num;
    private boolean active;
}
