package com.example.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/")
    public String getHealth() {
        return "healthCheck";
    }

    @GetMapping("/user/{name}")
    public String getPatientInfo(HttpServletRequest request, @PathVariable("name") String name) {
        return name;
    }
}
