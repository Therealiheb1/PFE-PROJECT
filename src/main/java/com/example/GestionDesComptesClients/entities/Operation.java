package com.example.GestionDesComptesClients.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", referencedColumnName = "rib")
    private Cbank sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver", referencedColumnName = "rib")
    private Cbank receiver;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "Date")
    private LocalDate dateV;
    @Column(name = "motif")
    private String motif;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
