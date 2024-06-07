package com.example.GestionDesComptesClients.service;

import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.entities.*;
import com.example.GestionDesComptesClients.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustService {

    @Autowired
    private CustomerRepo custRepo;

    @Autowired
    private CbankRepo cbankRepo;

    @Autowired
    private NumeroCompteRepo numCompteRepo;

    @Autowired
    private TypeCompteRepo typeCompteRepo;

    @Autowired
    private OperationRepository operationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransferService transferService;

    @Autowired
    private MailSservice mailService;

    public List<Customer> getAllCustomers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> pageResult = custRepo.findAll(pageable);
        return pageResult.getContent();
    }
    public Customer createCustomer(Customer customer) {

        return custRepo.save(customer);
    }
    public Customer editCustomer(String cin, Customer updatedCustomerDetails) {
        Customer existingCustomer = custRepo.findByCin(cin);
        if (existingCustomer != null) {
            existingCustomer.setFirstName(updatedCustomerDetails.getFirstName());
            existingCustomer.setLastName(updatedCustomerDetails.getLastName());
            existingCustomer.setCin(updatedCustomerDetails.getCin());
            existingCustomer.setSexe(updatedCustomerDetails.getSexe());
            existingCustomer.setTel(updatedCustomerDetails.getTel());
            existingCustomer.setEmail(updatedCustomerDetails.getEmail());
            existingCustomer.setAgence(updatedCustomerDetails.getAgence());
            existingCustomer.setProfession(updatedCustomerDetails.getProfession());
            existingCustomer.setDateN(updatedCustomerDetails.getDateN());
            return custRepo.save(existingCustomer);
        }
        return null;
    }

    public Customer getCustomerByCin(String cin) {
        return custRepo.findByCin(cin);
    }

    public String compareCin(String cin) {
        Customer storedCin = custRepo.findByCin(cin);
        if (storedCin != null && cin.equals(storedCin.getCin())) {
            return "CIN values match!";
        } else {
            return "CIN values do not match.";
        }
    }

    public Cbank addCompte(String cin) {
        Customer customer = custRepo.findByCin(cin);
        NumeroCompte numeroCompte = null;
        if (customer != null) {
            List<Cbank> existingAccounts = cbankRepo.findByClient(customer);
            if (existingAccounts.isEmpty()) {
                numeroCompte = createNumeroCompte();
            } else {
                numeroCompte = existingAccounts.get(0).getNumCompte();
            }

            String Rib = generateRib();
            String Iban = "TN5904" + (new Random().nextInt(100) + Rib + new Random().nextInt(100));

            Cbank newCompte = Cbank.builder()
                    .rib(Rib)
                    .client(customer)
                    .numCompte(numeroCompte)
                    .iban(Iban)
                    .solde(0.0)
                    .status(true)
                    .build();

            String emailBody = "Bienvenue à ATTIJARI BANK et Merci pour Votre Confiance. Nous avons le plaisir de vous accueillir en tant que nouveau client de ATTIJARI BANK. Merci de nous avoir choisis pour répondre à vos besoins financiers. Votre satisfaction est notre priorité. Nous nous engageons à vous fournir des services bancaires de qualité, adaptés à vos attentes et besoins. \n Votre RIB: " + Rib + "\n Votre IBAN: " + Iban;

            send(getCustomerEmailByCin(cin), "Bienvenue à ATTIJARI BANK et Merci pour Votre Confiance", emailBody);
            return cbankRepo.save(newCompte);
        }
        return null;
    }

    public String generateRib() {
        return String.valueOf((100000000 + new Random().nextInt(900000000)));
    }

    public NumeroCompte createNumeroCompte() {
        NumeroCompte numeroCompte = new NumeroCompte();
        numeroCompte.setNum(String.valueOf(System.currentTimeMillis() % 10000000));
        return numCompteRepo.save(numeroCompte);
    }

    public String archiveAccountByRib(String rib) {
        Cbank cbank = cbankRepo.findById(rib).orElse(null);
        if (cbank != null) {
            boolean status = cbank.getStatus();
            cbank.setStatus(!status);
            cbankRepo.save(cbank);
            send(getCustomerEmailByRib(rib), "Détail de votre compte", "");
            return status ? "Account with RIB " + rib + " has been archived." : "Account with RIB " + rib + " has been activated.";
        } else {
            return "Account not found with RIB: " + rib;
        }
    }

    public List<Cbank> getAllComptes(long id, int page, int size, boolean status, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Cbank> pageResult = cbankRepo.findByClientIdAndStatus(id, status, pageable);
        return pageResult.getContent();
    }

    public String transferFunds(String senderRib, String receiverRib, Double value) {
        try {
            transferService.transferFunds(senderRib, receiverRib, value);
            Optional<Cbank> senderOptional = cbankRepo.findByRib(senderRib);
            Optional<Cbank> receiverOptional = cbankRepo.findByRib(receiverRib);
            if (senderOptional.isPresent() && receiverOptional.isPresent()) {
                Cbank sender = senderOptional.get();
                Cbank receiver = receiverOptional.get();

                Operation virment = new Operation();
                virment.setSender(sender);
                virment.setReceiver(receiver);
                virment.setValue(value);
                virment.setDateV(LocalDate.now());
                virment.setStatus(true);
                operationRepository.save(virment);

                String emailBody = "Nous vous remercions de votre confiance en choisissant ATTIJARI BANK pour vos besoins financiers. Nous tenons à vous informer que votre récente transaction a été effectuée avec succès. Montant envoyé : " + value + "DT \n Date de la transaction : " + LocalDate.now() + "\n Compte débité : " + senderRib + "\n Compte créditeur : " + receiverRib;

                String senderEmail = getCustomerEmailByRib(senderRib);
                String receiverEmail = getCustomerEmailByRib(receiverRib);

                send(senderEmail, "Détail de votre Transaction Bancaire", emailBody);
                send(receiverEmail, "Détail de votre Transaction Bancaire", emailBody);

                return "Transfer successful";
            } else {
                return "Sender or receiver not found";
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public Cbank addSolde(String rib, Double amount) {
        send(getCustomerEmailByRib(rib), "Détail de votre solde Bancaire", "Nous sommes heureux de vous informer qu'un montant de " + amount + " a été ajouté à votre compte avec succès. Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter. Nous sommes là pour vous aider. Cordialement,");
        return transferService.addSolde(rib, amount);
    }

    public String changeAccountType(String rib, String newTypeId) {
        try {
            Cbank cbank = cbankRepo.findByRib(rib).orElseThrow(() -> new RuntimeException("Account with RIB " + rib + " not found"));
            TypeCompte newType = typeCompteRepo.findById(newTypeId).orElseThrow(() -> new RuntimeException("Type with ID " + newTypeId + " not found"));

            cbank.getTypes().clear();
            cbank.getTypes().add(newType);

            cbankRepo.save(cbank);
            return "Account type updated successfully for account with RIB: " + rib;
        } catch (Exception e) {
            return "Failed to update account type: " + e.getMessage();
        }
    }

    public String getCustomerEmailByRib(String rib) {
        Cbank cbank = cbankRepo.findByRib(rib).orElseThrow(() -> new RuntimeException("Account with RIB " + rib + " not found"));
        return cbank.getClient().getEmail();
    }

    public String getCustomerEmailByCin(String cin) {
        Customer customer = custRepo.findByCin(cin);
        return customer != null ? customer.getEmail() : "not found !!!!!!!!!!!!";
    }

    public String send(String to, String subject, String text) {
        try {
            mailService.sendEmail(to, subject, text);
            return "Your mail has been sent to the user.";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    } public List<Customer> filterCustomersByKeyword(String keyword) {
        String jpql = "SELECT c FROM Customer c WHERE " +
                "c.firstName LIKE :keyword " +
                "OR c.lastName LIKE :keyword " +
                "OR c.cin LIKE :keyword " +
                "OR c.sexe LIKE :keyword " +
                "OR c.tel LIKE :keyword " +
                "OR c.email LIKE :keyword " +
                "OR c.agence LIKE :keyword " +
                "OR c.profession LIKE :keyword";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("keyword", "%" + keyword + "%");

        return query.getResultList();
    }
//    @Transactional
//    public Page<Customer> filterCustomers(Map<String, String> params, Pageable pageable) {
//        System.out.println("Request Parameters:");
//        StringBuilder jpql = new StringBuilder("SELECT c FROM Customer c WHERE 1=1");
//        Map<String, Object> parameters = new HashMap<>();
//
//        Map<String, String> paramsNotNull = params.entrySet().stream().filter(el-> el.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey , entry->entry.getValue()));
//        for (Map.Entry<String, String> entry : paramsNotNull.entrySet()) {
//            if (entry.getKey() !="keyword") {
//                jpql.append(" AND c." + entry.getKey() + " LIKE :" + entry.getKey());
//                parameters.put(entry.getKey(), "%" + entry.getValue() + "%");
//            }else{
//                String keyword = "%" + entry.getValue() + "%";
//                jpql.append(" AND (c.firstName LIKE :keyword OR c.lastName LIKE :keyword OR c.cin LIKE :keyword OR c.sexe LIKE :keyword OR c.tel LIKE :keyword OR c.tel LIKE :keyword OR c.email LIKE :keyword  OR c.agence LIKE :keyword OR c.profession LIKE :keyword)");
//                parameters.put("keyword", keyword);
//            }
//        }
//
//        Query query = entityManager.createQuery(jpql.toString());
//        int totalResults = query.getResultList().size();
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize());
//        return new PageImpl<>(query.getResultList(), pageable, totalResults);
//    }
@Transactional
public Page<Customer> filterCustomers(Map<String, String> params, Pageable pageable) {
    System.out.println("Request Parameters: " + params);

    StringBuilder jpql = new StringBuilder("SELECT c FROM Customer c WHERE 1=1");
    Map<String, Object> parameters = new HashMap<>();

    Map<String, String> paramsNotNull = params.entrySet().stream()
            .filter(el -> el.getValue() != null && !el.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    for (Map.Entry<String, String> entry : paramsNotNull.entrySet()) {
        if (!entry.getKey().equals("keyword")) {
            jpql.append(" AND c.").append(entry.getKey()).append(" LIKE :").append(entry.getKey());
            parameters.put(entry.getKey(), "%" + entry.getValue() + "%");
        } else {
            String keyword = "%" + entry.getValue() + "%";
            jpql.append(" AND (c.firstName LIKE :keyword OR c.lastName LIKE :keyword OR c.cin LIKE :keyword OR c.sexe LIKE :keyword OR c.tel LIKE :keyword OR c.email LIKE :keyword OR c.agence LIKE :keyword OR c.profession LIKE :keyword)");
            parameters.put("keyword", keyword);
        }
    }

    Query query = entityManager.createQuery(jpql.toString());

    for (Map.Entry<String, Object> param : parameters.entrySet()) {
        query.setParameter(param.getKey(), param.getValue());
    }

    int totalResults = query.getResultList().size();
    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    return new PageImpl<>(query.getResultList(), pageable, totalResults);
}

    public Customer mapCustomerDTOToCustomer(UserCustomerDTO userDTO) {
        Customer customer = new Customer();
        customer.setFirstName(userDTO.getFirstName());
        customer.setLastName(userDTO.getLastName());
        customer.setCin(userDTO.getCin());
        customer.setSexe(userDTO.getSexe());
        customer.setTel(userDTO.getTel());
        customer.setEmail(userDTO.getEmail());
        customer.setAgence(userDTO.getAgence());
        customer.setProfession(userDTO.getProfession());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        customer.setDateN(LocalDate.parse(userDTO.getDateN(), formatter));
        return customer;
    }
}
