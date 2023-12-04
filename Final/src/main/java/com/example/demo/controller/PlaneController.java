package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.entity.Plane;
import com.example.demo.service.AirportService;
import com.example.demo.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class PlaneController {
    @Autowired
    private PlaneService planeService;
    @GetMapping("/plane")
    public String showPlanes(Model model){
        List<Plane> planes = planeService.getAllPlanes("Disabled");
        model.addAttribute("planeList", planes);
        model.addAttribute("prefix", "plane");
        return "Admin/plane-manager";
    }

    @PostMapping("/plane/save")
    public String savePlane(Plane plane){
        plane.setStatus("Active");
        planeService.save(plane);
        return "redirect:/Admin/plane";
    }
    @PostMapping("/plane/update/{id}")
    public String updatePlane(@PathVariable("id") Integer planeId, Plane plane){
        Optional<Plane> exist = planeService.getPlaneById(planeId);
        if(exist.isPresent()){
            plane.setPlaneId(planeId);
            planeService.update(planeId, plane);
            return "redirect:/Admin/plane";
        } else {
            return null;
        }
    }

    @PostMapping("/plane/delete/{id}")
    public String deleteplane(@PathVariable("id") Integer planeId) {

        Plane plane = planeService.getPlaneById(planeId).orElse(null);
        if(plane == null) {
            return "redirect:/Admin/plane";
        }
        plane.setStatus("Disabled");
        planeService.save(plane);
        return "redirect:/Admin/plane";

    }

}
