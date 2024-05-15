package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.repository.CbankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CbankService {

    private final CbankRepo cbankRepo;

    @Autowired
    public CbankService(CbankRepo cbankRepo){
        this.cbankRepo=cbankRepo;
    }

}

