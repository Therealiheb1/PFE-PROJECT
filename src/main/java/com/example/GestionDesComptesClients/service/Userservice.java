package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class Userservice {
    private final UserRepo userrepo;
@Autowired
    public Userservice(UserRepo userrepo) {
        this.userrepo = userrepo;
    }
    public User addUser(User user){
    user.setUserCode(UUID.randomUUID().toString());
    return userrepo.save(user);

    }
    public List<User> findAllUsers(){
    return userrepo.findAll();
    }

    public User updateUser (User user){
    return userrepo.save(user);
    }
    public void deleteUser (Long id){
    userrepo.deleteUserById(id);
    }
}
