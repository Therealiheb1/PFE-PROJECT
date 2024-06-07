package com.example.GestionDesComptesClients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.GestionDesComptesClients.entities.Notf;


import javax.management.Notification;
import java.util.List;






import java.util.List;

public interface NotificationRepository extends JpaRepository<Notf, Long> {
    List<Notf> findBySenderInOrReceiverIn(List<String> senders, List<String> receivers);
}

