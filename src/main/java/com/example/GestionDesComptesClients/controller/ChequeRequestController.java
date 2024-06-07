package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.entities.ChequeRequest;
import com.example.GestionDesComptesClients.service.ChequeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cheque")
public class ChequeRequestController {

   @Autowired
   private ChequeRequestService chequeRequestService;

   @PostMapping("/chequeReq")
   public ResponseEntity<String> createChequeRequest() {
      String response = chequeRequestService.createChequeRequest();
      if (response.equals("Customer not found")) {
         return ResponseEntity.badRequest().body(response);
      }
      return ResponseEntity.ok(response);
   }

   @GetMapping("/requests")
   public ResponseEntity<List<Map<String, String>>> getChequeRequestsByRole() {
      try {
         List<Map<String, String>> response = chequeRequestService.getChequeRequestsByRole();
         return ResponseEntity.ok(response);
      } catch (RuntimeException e) {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
      }
   }

   @PutMapping("/updateCurrentStep/{customerId}")
   public ResponseEntity<String> updateCurrentStep(@PathVariable Long customerId) {
      String response = chequeRequestService.updateCurrentStep(customerId);
      if (response.equals("Cheque request not found") || response.equals("User not authorized to update current step")) {
         return ResponseEntity.badRequest().body(response);
      }
      return ResponseEntity.ok(response);
   }

   @GetMapping("/chequeStatus")
   public ResponseEntity<String> getChequeRequestStatus() {
      String response = chequeRequestService.getChequeRequestStatus();
      if (response.equals("Customer not found") || response.equals("Cheque request not found")) {
         return ResponseEntity.badRequest().body(response);
      }
      return ResponseEntity.ok(response);
   }
}
