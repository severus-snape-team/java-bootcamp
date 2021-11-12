package com.bootcamp.demo.controller;

import com.bootcamp.demo.FirebaseController;
import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.service.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/firebase")
public class ScooterController {

    private final ScooterService scooterService;

    @Autowired
    public ScooterController(ScooterService scooterService){
        this.scooterService = scooterService;
    }

    @GetMapping( "/createScooter")
    public String register(Model model) {
        Scooter scooter = new Scooter();
        model.addAttribute("scooter", scooter);
        return "createScooterForm";
    }

    @PostMapping("/createScooter")
    public String submitForm(@ModelAttribute("scooter") Scooter scooter) {
        this.scooterService.insertScooter(scooter);
        return "createScooterForm";
    }

    /**
     * Returning an html page with all the scooters listed (without details)
     */
    @GetMapping("/scooters")
    public String viewAllScooters(Model model){
        model.addAttribute("scooters", this.scooterService.returnAllScooters());
        /*for(String s : this.scooterService.returnAllScooters()){
            System.out.println(s);
        }*/
        return "listScooters";
    }

}
