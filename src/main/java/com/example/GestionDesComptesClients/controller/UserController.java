package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.DTO.UserCustomerDTO;
import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.service.UserServ;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserServ userService;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody UserCustomerDTO userDTO) {
        return ResponseEntity.ok().body(userService.createUser(userDTO)) ;
    }

    @PutMapping("/update/{username}")
    public Response updateUser(@PathVariable("username") String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }

    @PutMapping("/updatePassword/{username}")
    public Response updatePassword(@PathVariable("username") String username, @RequestParam("newPassword") String newPassword) {
        return userService.updatePassword(username, newPassword);
    }
}
