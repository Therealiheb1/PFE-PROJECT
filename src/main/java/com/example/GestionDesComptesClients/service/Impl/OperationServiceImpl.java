package com.example.GestionDesComptesClients.service.Impl;

import com.example.GestionDesComptesClients.entities.Compte;
import com.example.GestionDesComptesClients.entities.Retrait;
import com.example.GestionDesComptesClients.entities.Versement;
import com.example.GestionDesComptesClients.repository.CompteRepo;
import com.example.GestionDesComptesClients.repository.OperationRepo;
import com.example.GestionDesComptesClients.service.OperationServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Transactional
public class OperationServiceImpl implements OperationServices {
    @Autowired
    private CompteRepo compteRepo;
    @Autowired
    private OperationRepo operationRepo;

    @Override
    public Compte consulterCompte(String codeCompte) {
        return compteRepo.findById(codeCompte).orElse(null);
    }
    @Override
    public void verser(String codeCompte, double montant) {

        Compte compte = compteRepo.findById(codeCompte).orElse(null);
        Versement versement = new Versement(new Date(), montant, compte);
        operationRepo.save(versement);
        if (compte != null) {
            compte.setSolde(compte.getSolde() + montant);
        }
        assert compte != null;
        compteRepo.save(compte);
    }
    @Override
    public void retirer(String codeCompte, double montant) {
        Compte compte = compteRepo.findById(codeCompte).orElse(null);
        Retrait retrait = new Retrait(new Date(), montant, compte);
        operationRepo.save(retrait);
        if (compte != null) {
            compte.setSolde(compte.getSolde() - montant);
        }
        assert compte != null;
        compteRepo.save(compte);
    }
    @Override
    public void virement(String codeCompte1, String codeCompte2, double montant)  {
        if (codeCompte1.equals(codeCompte2)){
            try {
                throw new Exception("le virement d'un compte vers ce meme compte n'est pas autorisé");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        retirer(codeCompte1,montant);
        verser(codeCompte2,montant);
    }
}
