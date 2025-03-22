package com.sparta.slackservice.presentation;

import com.sparta.commonmodule.aop.RoleCheck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/slacks")
public class SlackController {

    @RoleCheck("ROLE_MASTER,ROLE_COMPANY")
    @GetMapping("/test")
    public String getSlack() {
        return "Hello World";
    }
}
