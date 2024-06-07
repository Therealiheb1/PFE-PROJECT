package com.example.GestionDesComptesClients.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCustomerDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> realmRoles;
    private String tel;
    private String cin;
    private String sexe;
    private String agence;
    private String profession;
    private String dateN;

}

