package com.sparta.slackservice.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/slacks")
public class SlackController {
    @GetMapping("/test")
    public String getSlack() {
        return "Hello World";
    }
}
