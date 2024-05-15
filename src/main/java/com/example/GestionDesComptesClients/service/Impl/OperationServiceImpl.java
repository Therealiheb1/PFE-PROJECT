package com.example.GestionDesComptesClients.service.Impl;

import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Retrait;
import com.example.GestionDesComptesClients.entities.Versement;
import com.example.GestionDesComptesClients.repository.CbankRepo;
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
    private CbankRepo cbankRepo;
    @Autowired
    private OperationRepo operationRepo;

    @Override
    public Cbank consulterCompte(String codeCompte) {
        return cbankRepo.findById(codeCompte).orElse(null); // Parse String to Long
    }

    @Override
    public void verser(String codeCompte, double montant) {

        Cbank compte = cbankRepo.findById(codeCompte).orElse(null);
        Versement versement = new Versement(new Date(), montant, compte);
        operationRepo.save(versement);
        if (compte != null) {
            compte.setSolde(compte.getSolde() + montant);
        }
        assert compte != null;
        cbankRepo.save(compte);
    }


    @Override
    public void retirer(String codeCompte, double montant) {
        Cbank compte = cbankRepo.findById(codeCompte).orElse(null);
        Retrait retrait = new Retrait(new Date(), montant, compte);
        operationRepo.save(retrait);
        if (compte != null) {
            compte.setSolde(compte.getSolde() - montant);
        }
        assert compte != null;
        cbankRepo.save(compte);
    }

    @Override
    public void virement(String codeCompte1, String codeCompte2, double montant) {
        if (codeCompte1.equals(codeCompte2)) {
            try {
                throw new Exception("le virement d'un compte vers ce meme compte n'est pas autorisé");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        retirer(codeCompte1, montant);
        verser(codeCompte2, montant);
    }
}
