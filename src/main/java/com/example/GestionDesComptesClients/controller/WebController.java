package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.service.Userservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    @Autowired
    private Userservice userService;

            @GetMapping(path = "/userInfo")
    public String getUserInfo1() {
       userService.getLoggedUser();
return "";
       // return "UserInfo1: " + user.getNom_prenom()  + ", " + user.getEmail();
    }


}