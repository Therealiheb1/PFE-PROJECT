//    package com.example.GestionDesComptesClients.service;
//
//    import com.example.GestionDesComptesClients.entities.Notf;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.stereotype.Service;
//
//    import com.example.GestionDesComptesClients.entities.Customer;
//    import com.example.GestionDesComptesClients.repository.NotificationRepository;
//
//    import javax.management.Notification;
//    import java.util.ArrayList;
//    import java.util.List;
//
//    @Service
//    public class NotificationService {
//
//        @Autowired
//        private NotificationRepository notificationRepository;
//
//        @Autowired
//        private CustService custService;
//
//        public List<Notification> getNotificationsForCustomer(String keycloakId) {
//            Customer customer = custService.findByKeyId(keycloakId);
//
//            List<String> senderReceiverList = new ArrayList<>();
//            senderReceiverList.add(customer.getKeyId());
//
//            List<Notf> notifications = notificationRepository.findBySenderInOrReceiverIn(senderReceiverList, senderReceiverList);
//
//            // Convert Notf objects to Notification objects if needed
//            List<Notification> result = new ArrayList<>();
//            for (Notf notf : notifications) {
//                Notification notification = new Notification();
//                notification.setSender(notf.getSender());
//                notification.setReceiver(notf.getReceiver());
//                notification.setValue(notf.getValue());
//                notification.setStatus(notf.getStatus());
//                result.add(notification);
//            }
//
//            return result;
//        }
//
//    }
