package com.bootcamp.demo.controller;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.service.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/firebase")
public class ScooterController {

    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping( "/createScooter")
    public String register(Model model) {
        Scooter scooter = new Scooter();
        model.addAttribute("scooter", scooter);
        return "createScooterForm";
    }

    @PostMapping("/createScooter")
    public String submitForm(@Valid @ModelAttribute("scooter") Scooter scooter, BindingResult bindingResult, Model m) {
        if (!bindingResult.hasErrors()) {
            this.scooterService.insertScooter(scooter);
            m.addAttribute("message", "Successfully added...");
        }
        return "createScooterForm";
    }

    /**
     * Returning an html page with all the scooters listed (without details)
     */
    @GetMapping("/scooters")
    public String viewAllScooters(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("scooters", this.scooterService.returnAllScooters());
        return "listScooters";
    }

    @GetMapping(path = "/getAllPaths", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Set<String> getAllPaths() {
        return this.scooterService.getAllPaths();
    }
}
