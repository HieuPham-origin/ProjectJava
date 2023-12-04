package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booking")
public class BookingController {
    @GetMapping(value = {"", "/"})
    public String booking(Model model) {
        model.addAttribute("flight1");
        model.addAttribute("flight1");
        return "booking";
    }

    @GetMapping("/review")
    public String review() {
        return "booking-review";
    }

    @GetMapping("/complete")
    public String complete() {
        return "booking-complete";
    }
}
