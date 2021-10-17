package com.jwt.controller;

import com.jwt.entity.User;
import com.jwt.repostiroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class MsgController {

    @Autowired
    private  UserRepository userRepository;

    @GetMapping
    public ResponseEntity<String> getMessage(){
        return ResponseEntity.ok().body("Salamlar");
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }



}
