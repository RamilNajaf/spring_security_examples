package com.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MsgController {

    @GetMapping
    public ResponseEntity<String> getMessage(){
        return ResponseEntity.ok().body("Salamlar");
    }




}
