package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.entities.ChequeRequest;
import com.example.GestionDesComptesClients.entities.Customer;
import com.example.GestionDesComptesClients.repository.ChequeRequestRepository;
import com.example.GestionDesComptesClients.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChequeRequestService {
    @Autowired
    private ChequeRequestRepository chequeRequestRepository;

    @Autowired
    private CustomerRepo customerRepository;

    public String createChequeRequest() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String keycloakId = authentication.getToken().getSubject();

        Optional<Customer> customerOptional = customerRepository.findByKeyId(keycloakId);
        if (!customerOptional.isPresent()) {
            return "Customer not found";
        }

        Customer customer = customerOptional.get();
        ChequeRequest chequeRequest = ChequeRequest.builder()
                .customer(customer)
                .status(true)
                .currentStep(1)
                .build();

        chequeRequestRepository.save(chequeRequest);
        return "Cheque request created successfully";
    }

    public List<Map<String, String>> getChequeRequestsByRole() {
        List<String> roles = getRoles();
        if (roles.contains("admin")) {
            String keycloakId = getKeycloakId();
            Optional<Customer> customerOptional = customerRepository.findByKeyId(keycloakId);

            if (customerOptional.isPresent()) {
                List<ChequeRequest> requests = chequeRequestRepository.findAllByCustomer_IdAndStatus(customerOptional.get().getId(), true);
                for (ChequeRequest request : requests) {
                    request.setCurrentStep(2);
                }
                chequeRequestRepository.saveAll(requests);
                return mapChequeRequestsToResponse(requests);
            } else {
                return Collections.singletonList(Collections.singletonMap("error", "Customer not found"));
            }
        } else {
            throw new RuntimeException("User not authorized");
        }
    }

    public String updateCurrentStep(Long customerId) {
        Optional<ChequeRequest> optionalChequeRequest = chequeRequestRepository.findByCustomer_Id(customerId);
        if (optionalChequeRequest.isEmpty()) {
            return "Cheque request not found";
        }
        List<String> roles = getRoles();
        if (roles.contains("admin")) {
            ChequeRequest chequeRequest = optionalChequeRequest.get();
            chequeRequest.setCurrentStep(3);
            chequeRequest.setStatus(false);
            chequeRequestRepository.save(chequeRequest);
            return "Current step updated to 3 for admin";
        } else {
            return "User not authorized to update current step";
        }
    }

    public String getChequeRequestStatus() {
        String keycloakId = getKeycloakId();
        Optional<Customer> customerOptional = customerRepository.findByKeyId(keycloakId);

        if (!customerOptional.isPresent()) {
            return "Customer not found";
        }

        Optional<ChequeRequest> chequeRequestOptional = chequeRequestRepository.findByCustomer_Id(customerOptional.get().getId());

        if (!chequeRequestOptional.isPresent()) {
            return "Cheque request not found";
        }

        ChequeRequest chequeRequest = chequeRequestOptional.get();
        switch (chequeRequest.getCurrentStep()) {
            case 1:
                return "Votre demande est en cours de traitement";
            case 2:
                return "Demande traitée";
            case 3:
                return "Demande acceptée";
            default:
                return "Unknown status";
        }
    }

    private List<String> getRoles() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getToken().getClaims().containsKey("realm_access")) {
            Map<String, Object> realmAccess = (Map<String, Object>) authentication.getToken().getClaims().get("realm_access");
            if (realmAccess.containsKey("roles")) {
                List<String> rolesList = (List<String>) realmAccess.get("roles");
                return rolesList;
            }
        }
        return Collections.emptyList();
    }

    private String getKeycloakId() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getToken().getSubject();
    }

    private List<Map<String, String>> mapChequeRequestsToResponse(List<ChequeRequest> requests) {
        return requests.stream().map(request -> {
            Map<String, String> map = new HashMap<>();
            map.put("firstName", request.getCustomer().getFirstName());
            map.put("lastName", request.getCustomer().getLastName());
            map.put("cin", request.getCustomer().getCin());
            map.put("email", request.getCustomer().getEmail());
            map.put("id", request.getCustomer().getId().toString());
            return map;
        }).collect(Collectors.toList());
    }

    public List<ChequeRequest> getChequeRequestsByStep(int step) {
        return chequeRequestRepository.findByCurrentStep(step);
    }
}
