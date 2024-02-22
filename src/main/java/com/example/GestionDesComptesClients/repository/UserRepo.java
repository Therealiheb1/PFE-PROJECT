package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    void deleteUserById(Long id);
}
