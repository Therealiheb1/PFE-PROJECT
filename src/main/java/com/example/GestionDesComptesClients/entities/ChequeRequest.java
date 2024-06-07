package com.example.GestionDesComptesClients.entities;

import com.example.GestionDesComptesClients.repository.ChequeRequestStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cheque_request")
public class ChequeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(name = "status")
    private Boolean status;
    @Column(name = "current_step")
    private Integer currentStep;
}
