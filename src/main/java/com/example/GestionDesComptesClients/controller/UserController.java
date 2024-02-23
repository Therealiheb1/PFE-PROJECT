package com.example.GestionDesComptesClients.controller;

import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.service.Userservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {
    private final Userservice userservice;

    public UserController(Userservice userservice) {
        this.userservice = userservice;
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userservice.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User Newuser = userservice.addUser(user);
        return new ResponseEntity<>(Newuser, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<User> UpdateUser(@RequestBody User user){
        User UpdateUser = userservice.updateUser(user);
        return new ResponseEntity<>(UpdateUser, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable("id") Long id){
        userservice.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
