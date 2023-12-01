package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Plane;
import com.example.demo.service.AirportService;
import com.example.demo.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/Admin")
public class PlaneController {
    @Autowired
    private PlaneService planeService;
    @GetMapping("/plane")
    public String showPlanes(Model model){
        List<Plane> planes = planeService.getAllPlanes();
        model.addAttribute("planeList", planes);
        model.addAttribute("prefix", "plane");
        return "Admin/plane-manager";
    }
    @PostMapping("/plane/save")
    public String savePlane(Plane plane){
        planeService.save(plane);
        return "redirect:/Admin/plane";
    }
}
