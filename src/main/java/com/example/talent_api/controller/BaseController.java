package com.example.talent_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BaseController {

    @GetMapping
    public String baseResponse() {
        return "This is the Talent-API!";
    }
}