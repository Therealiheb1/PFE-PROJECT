package com.example.GestionDesComptesClients.service;


import com.example.GestionDesComptesClients.entities.Cbank;
import com.example.GestionDesComptesClients.entities.Notf;
import com.example.GestionDesComptesClients.repository.CbankRepo;
import com.example.GestionDesComptesClients.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private CbankRepo cbankRepository;

    @Autowired
    private NotificationRepository notfRepository;


    public void transferFunds(String senderRib, String receiverRib, Double value) {
        Optional<Cbank> senderAccountOptional = cbankRepository.findByRib(senderRib);
        Optional<Cbank> receiverAccountOptional = cbankRepository.findByRib(receiverRib);

        if (!senderAccountOptional.isPresent() || !receiverAccountOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid sender or receiver Rib");
        }

        Cbank senderAccount = senderAccountOptional.get();
        Cbank receiverAccount = receiverAccountOptional.get();

        if (senderAccount.getSolde() < value) {
            throw new IllegalArgumentException("MA3ANDEKESH FLOUS");
        }





        senderAccount.setSolde(senderAccount.getSolde() - value);
        receiverAccount.setSolde(receiverAccount.getSolde() + value);





        cbankRepository.save(senderAccount);
        cbankRepository.save(receiverAccount);

        Notf notification = Notf.builder()
                .sender(senderAccount.getRib())
                .receiver(receiverAccount.getRib())
                .value(value)
                .status(true)
                .build();

        notfRepository.save(notification);
    }
    public Cbank addSolde(String rib, Double amount) {
        Optional<Cbank> cbankOptional = cbankRepository.findByRib(rib);

        if (cbankOptional.isPresent()) {
            Cbank cbank = cbankOptional.get();
            Double newSolde = cbank.getSolde() + amount;
            cbank.setSolde(newSolde);
            return cbankRepository.save(cbank);
        } else {
            throw new IllegalArgumentException("Account with RIB " + rib + " not found");
        }
    }
}