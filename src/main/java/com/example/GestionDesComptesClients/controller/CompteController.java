package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.entities.Compte;
import com.example.GestionDesComptesClients.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compte")

public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }
    @PostMapping("/add")
    public ResponseEntity<Compte> addcompte(@RequestBody Compte compte){
        Compte newcompte = compteService.addcompte(compte);
        return new ResponseEntity<>(newcompte, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Compte> updatecompte(@RequestBody Compte compte){
        Compte updatecompte = compteService.updateCompte(compte);
        return new ResponseEntity<>(updatecompte, HttpStatus.OK);
    }

}
