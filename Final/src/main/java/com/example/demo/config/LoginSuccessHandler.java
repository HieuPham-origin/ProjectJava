package com.example.demo.config;

import com.example.demo.config.jwt.JwtTokenUtil;
import com.example.demo.dto.AccountDetails;
import com.example.demo.entity.Account;
import com.example.demo.entity.Passenger;
import com.example.demo.service.AccountService;
import com.example.demo.service.PassengerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private PassengerService passengerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = accountService
                .loadUserByUsername(accountDetails.getUsername());

        String jwt = jwtTokenUtil.generateToken(userDetails);

        response.setStatus(HttpServletResponse.SC_OK);

        Account account = accountDetails.getAccount();
        Passenger passenger = passengerService.getPassengerByEmail(accountDetails.getAccount().getUsername());
        String role = accountDetails.getAccount().getRole();

        response.addHeader("Authorization", "Bearer " + jwt);

        // Get or create HttpSession
        HttpSession session = request.getSession();
        session.setAttribute("jwtToken", jwt);

        if (role.equals("ADMIN")) {
            session.setAttribute("admin", account);
            response.sendRedirect("/Admin/airport");
        } else {
            session.setAttribute("sessionAccount", account);
            session.setAttribute("sessionPassenger", passenger);
            response.sendRedirect("/index");
        }
    }

}
