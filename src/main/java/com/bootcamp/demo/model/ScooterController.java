package com.bootcamp.demo.model;

import com.bootcamp.demo.FirebaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScooterController {

    @Autowired
    FirebaseController firestoreController;

    @GetMapping( "/firebase/createScooter")
    public String register(Model model) {
        Scooter scooter = new Scooter();
        model.addAttribute("scooter", scooter);
        return "createScooterForm";
    }

    @PostMapping("/firebase/createScooter")
    public String submitForm(@ModelAttribute("scooter") Scooter scooter) {
        this.firestoreController.insertScooter(scooter);
        return "createScooterForm";
    }

}
