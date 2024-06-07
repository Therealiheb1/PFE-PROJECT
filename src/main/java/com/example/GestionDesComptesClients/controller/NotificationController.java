//package com.example.GestionDesComptesClients.controller;
//
//import com.example.GestionDesComptesClients.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.management.Notification;
//import java.util.List;
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/notf")
//public class NotificationController {
//    @Autowired
//    private NotificationService notificationService;
//
//    @MessageMapping("/subscribe")
//    @SendTo("/topic/notifications")
//    public List<Notification> subscribe(String keycloakId) {
//
//        return notificationService.getNotificationsForCustomer(keycloakId);
//    }
//}
