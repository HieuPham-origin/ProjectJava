package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Airport;
import com.example.demo.entity.CustomerType;
import com.example.demo.entity.Passenger;
import com.example.demo.repository.CustomerTypeRepository;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final CustomerTypeService customerTypeService;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AirportService airportService;
    public AccountController(AccountService accountService, PassengerService passengerService, CustomerTypeService customerTypeService) {
        this.accountService = accountService;
        this.passengerService = passengerService;
        this.customerTypeService = customerTypeService;

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

        System.out.println("Login process");
        String encryptedPassword = passwordEncoder.encode(password);

        System.out.println("Password is :" + password);

        if (username.isEmpty() || encryptedPassword.isEmpty()) {
            redirectAttributes.addAttribute("error", "Please provide both username and password");
            return "redirect:/login";
        }

        if(!accountService.authorize(username,encryptedPassword)) {
            redirectAttributes.addAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }

        Account account = accountService.getAccountByUsername(username);
        Passenger passenger = passengerService.getPassengerByEmail(username);
        String role = account.getRole();
        if(role.equals("ADMIN")) {
            session.setAttribute("admin", account);
            return "redirect:/Admin/airport";

        }
        else {
            String email = passenger.getEmail();
            Account userAccount = accountService.getAccountByUsername(email);
            if (userAccount == null) {
                return "redirect:/login";
            }
            model.addAttribute("passenger", passenger);
            session.setAttribute("sessionPassenger", passenger);
            session.setAttribute("sessionAccount", userAccount);
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionPassenger");
        session.removeAttribute("admin");

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
        CustomerType defaultType = customerTypeService.getCustomerTypeById(1);
        System.out.println(defaultType.getTypeId());
        System.out.println(defaultType.getTypeName());
        passenger.setType(defaultType);
        account.setUsername(email);
        String encryptedPassword = passwordEncoder.encode(password);
        account.setPassword(encryptedPassword);
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
