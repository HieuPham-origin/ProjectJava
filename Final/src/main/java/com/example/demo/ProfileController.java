package com.example.demo;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Account;
import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.Passenger;
import com.example.demo.entity.Reservation;
import com.example.demo.service.AccountService;
import com.example.demo.service.PassengerService;
import com.example.demo.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    PassengerService passengerService;

    @Autowired
    AccountService accountService;
    @Autowired
    ReservationService reservationService;

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
        redirectAttributes.addFlashAttribute("resultMessage", "User information updated!");
        return "redirect:/profile";
    }

    @GetMapping("/password")
    public String password(HttpSession session, Model model) {
        Passenger user = (Passenger) session.getAttribute("sessionPassenger");
        if (user == null) {
            return "redirect:/login";
        }
        Account account = (Account) session.getAttribute("sessionAccount");
        if (account == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("account", account);
        return "profile-password";
    }

    @PostMapping("/password/update/{id}")
    public String updatePassword(@PathVariable("id") Integer accountId,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {

        Account account = (Account) session.getAttribute("sessionAccount");
        if (account == null) {
            return "redirect:/login";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // Check if the current password matches the one in the database
        if (!passwordEncoder.matches(currentPassword, account.getPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid current password");
            return "redirect:/profile/password";
        }


        // Check if the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New password and confirm password do not match");
            return "redirect:/profile/password";
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(hashedPassword);

        // Update the password
        accountService.updateAccount(account);
        redirectAttributes.addFlashAttribute("resultMessage", "Password updated successfully");

        return "redirect:/profile/password";
    }

    @GetMapping("/reservations")
    public String reservations(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("sessionAccount");
        if (account == null) {
            return "redirect:/login";
        }
        else{
            List<Reservation> reservations = reservationService.findByAccount(account);
            model.addAttribute("reservations",reservations);
            return "profile-reservation";
        }
    }
    @GetMapping("/reservations/{id}")
    public String getReservations(@PathVariable("id") Integer reservationId, Model model){
        Reservation reservation = reservationService.findById(reservationId);
        model.addAttribute("reservation", new ReservationDTO(reservation));
        return "booking-complete";
    }
}
