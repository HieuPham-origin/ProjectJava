package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping(value = {"", "/"})
    public String profile() {
        return "profile";
    }

    @GetMapping("/password")
    public String password() {
        return "profile-password";
    }

    @GetMapping("/reservations")
    public String reservations() {
        return "profile-reservation";
    }
}
