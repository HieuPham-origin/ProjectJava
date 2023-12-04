package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Airport;
import com.example.demo.entity.Passenger;
import com.example.demo.service.AccountService;
import com.example.demo.service.AirportService;
import com.example.demo.service.PassengerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final PassengerService passengerService;

    @Autowired
    private AirportService airportService;
    public AccountController(AccountService accountService, PassengerService passengerService) {
        this.accountService = accountService;
        this.passengerService = passengerService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        if (username.isEmpty() || password.isEmpty()) {
            redirectAttributes.addAttribute("error", "Please provide both username and password");
            return "redirect:/login";
        }

        if(!accountService.authorize(username,password)) {
            redirectAttributes.addAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
        Account account = accountService.getAccountByUsername(username);
        Passenger passenger = passengerService.getPassengerByEmail(username);
        String role = account.getRole();
        if(role.equals("admin")) {
            session.setAttribute("admin", passenger);
            return "redirect:/Admin/airport";

        }
        else {
            model.addAttribute("passenger", passenger);
            session.setAttribute("sessionPassenger", passenger);
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");

        // Redirect to the login page or any other desired page
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("mobile") String mobile,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           RedirectAttributes redirectAttributes) {

        // Perform validations on the input data
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addAttribute("error", "Password mismatch");
            return "redirect:/register";
        }

        if (accountService.emailExist(email)) {
            redirectAttributes.addAttribute("error", "Email already exist");
            return "redirect:/register";
        }
        // Create a new Account object with the provided data
        Account account = new Account();
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setPhoneNumber(mobile);
        passenger.setEmail(email);
//        passenger.setType();  // Default adult
        account.setUsername(email);
        account.setPassword(password);
        account.setRole("user"); // Default user

        //Save to db
        Account savedAccount = accountService.saveAccount(account);
        Passenger savedPassenger = passengerService.savePassenger(passenger);


        if (savedAccount == null || savedPassenger == null) {
            redirectAttributes.addAttribute("error", "Account creation failed");
            return "redirect:/register";
        }

        // Redirect to the login page after successful registration
        return "redirect:/login";
    }
//    @GetMapping("/index")
//    public String index(Model model){
//        List<Airport> airports = this.airportService.getAllAirports();
//        model.addAttribute("airports",airports);
//        return "index";
//    }


}
