package com.example.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    @GetMapping("/ip")
    public String getIp() {

        String ip = "Unknown";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ip;
    }

    @GetMapping("/host-name")
    public String getHostName() {

        String name = "Unknown";
        try {
            name = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return name;
    }
}
