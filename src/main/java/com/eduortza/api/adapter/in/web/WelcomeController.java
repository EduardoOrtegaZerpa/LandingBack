package com.eduortza.api.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class WelcomeController {

    Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @GetMapping("/")
    public String welcome() {
        logger.info("Welcome request received");
        return "Welcome to the API";
    }
}
