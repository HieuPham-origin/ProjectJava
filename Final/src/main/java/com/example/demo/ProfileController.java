package com.example.demo;

import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.Passenger;
import com.example.demo.service.AccountService;
import com.example.demo.service.PassengerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    PassengerService passengerService;

    @Autowired
    AccountService accountService;

    @GetMapping(value = {"", "/"})
    public String profile(HttpSession session, Model model) {
        Passenger user = (Passenger) session.getAttribute("sessionPassenger");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping(value = {"/update/{id}"})
    public String updateProfile(@PathVariable("id") Integer passengerId,
                                Passenger updatedPassenger,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        Passenger user = (Passenger) session.getAttribute("sessionPassenger");
        if (user == null) {
            return "redirect:/login";
        }
        user.setPassengerId(passengerId);
        user.setFirstName(updatedPassenger.getFirstName());
        user.setLastName(updatedPassenger.getLastName());
        user.setCountry(updatedPassenger.getCountry());
        user.setGender(updatedPassenger.getGender());
        user.setAddress(updatedPassenger.getAddress());

        Passenger newUser = passengerService.updatePassenger(user);
        session.setAttribute("sessionPassenger", newUser);
        return "redirect:/profile";
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
