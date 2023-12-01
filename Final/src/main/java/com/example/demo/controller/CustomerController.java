package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class CustomerController {
    @Autowired
    private AirportService airportService;
//    @PostMapping("/listflight")
//    public String listflight(){
//        return "listflight";
//    }

}
