package com.bootcamp.demo.controller;

import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.service.ScooterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import static java.lang.System.getProperty;
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
    public String submitForm(@ModelAttribute("scooter") Scooter scooter) {
        this.scooterService.insertScooter(scooter);

//        System.out.println(scooter.toString());

        return "redirect:/firebase/scooters";
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

    /**
     * Returning an html page for the scooter with DocumentName = scooterName (where scooterName is a path variable)
     */
    @GetMapping("/scooters/{scooterName}")
    public String viewScooter(@PathVariable String scooterName, Model model) {
        Scooter scooter = scooterService.getScooterByName(scooterName);
        if(scooter == null)
            return "redirect:/firebase/scooters";
        model.addAttribute("scooter", scooter);
        return "modifyScooterForm";
    }


    /**
     * Updates the scooter received from model if the "Update" button was pressed on getScooter page
     */
    @PostMapping(value = "/modifyScooter", params = "Update")
    public String updateScooter(@ModelAttribute("scooter") Scooter scooter){
        this.scooterService.insertScooter(scooter);
        return "redirect:/firebase/scooters";
    }

    /**
     * Deletes the scooter received from model if the "Delete" button was pressed on getScooter page
     */
    @PostMapping(value = "/modifyScooter", params = "Delete")
    public String deleteScooter(@ModelAttribute("scooter") Scooter scooter){
        this.scooterService.deleteScooter(scooter.getDocumentName());
        return "redirect:/firebase/scooters";
    }


    @GetMapping("/map")
    public String viewGoogleMaps(Model model) {
        model.addAttribute("mapsKey", getProperty("mapsKey"));
        return "map";
    }


}
