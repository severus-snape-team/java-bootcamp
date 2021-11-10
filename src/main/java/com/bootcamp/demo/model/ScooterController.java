package com.bootcamp.demo.model;

import com.bootcamp.demo.FirebaseController;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScooterController {

    @Autowired
    FirebaseController firestoreController;

    @GetMapping(value = "/firebase/create")
    public String register(Model model) {
        Scooter scooter = new Scooter();
        model.addAttribute("scooter", scooter);
        System.out.println("Submited GET");
        return "createScooterForm";
    }

    @PostMapping("/create")
    public String submitForm(@ModelAttribute("scooter") Scooter scooter) {
        System.out.println(scooter);
        this.firestoreController.insertScooter(scooter);
        return "createScooterForm";
    }

}
