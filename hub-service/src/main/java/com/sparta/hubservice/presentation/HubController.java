package com.sparta.hubservice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs")
public class HubController {

    @GetMapping
    public ResponseEntity<String> hubTest(){
        return ResponseEntity.ok("connect hub-service");
    }

}
