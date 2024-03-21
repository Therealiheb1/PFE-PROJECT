package com.example.GestionDesComptesClients.repository;

import com.example.GestionDesComptesClients.entities.User;
import jakarta.persistence.Entity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByid(Long id);
    User findByEmail(String email);
}
