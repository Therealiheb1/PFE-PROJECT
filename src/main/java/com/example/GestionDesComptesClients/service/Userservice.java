package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.User;


public interface Userservice {
//    UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord);
    public User getLoggedUser() ;

    public void syncUser(User user) ;
}
