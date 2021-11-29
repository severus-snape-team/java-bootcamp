package com.bootcamp.demo.controller;

import com.bootcamp.demo.controller.qrcode.QRCodeGenerator;
import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.service.ScooterService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/")
public class ScooterController {

    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping("/createScooter")
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

    /**
     * Returning an html page for the scooter with DocumentName = scooterName (where scooterName is a path variable)
     */
    @GetMapping("/scooters/{scooterName}")
    public String viewScooter(@PathVariable String scooterName, Model model) {
        try {
            byte[] image = QRCodeGenerator.getQRCodeImage("https://java-bootcamp.herokuapp.com/scooters/" + scooterName, 180, 180);
            String qrcode = Base64.getEncoder().encodeToString(image);
            model.addAttribute("qr_code_img", qrcode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        Scooter scooter = scooterService.getScooterByName(scooterName);
        if (scooter == null)
            return "redirect:/scooters";
        model.addAttribute("scooter", scooter);
        return "getScooter";
    }


    /**
     * Updates the scooter received from model if the "Update" button was pressed on getScooter page
     */
    @PostMapping(value = "/modifyScooter", params = "Update")
    public String updateScooter(@Valid @ModelAttribute("scooter") Scooter scooter,BindingResult bindingResult, Model m) {
        try {
            byte[] image = QRCodeGenerator.getQRCodeImage("https://java-bootcamp.herokuapp.com/scooters/" + scooter.getDocumentName(), 180, 180);
            String qrcode = Base64.getEncoder().encodeToString(image);
            m.addAttribute("qr_code_img", qrcode);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        if (!bindingResult.hasErrors()) {
            this.scooterService.insertScooter(scooter);
            m.addAttribute("message", "Successfully updated...");
        }
        return "getScooter";
    }

    /**
     * Deletes the scooter received from model if the "Delete" button was pressed on getScooter page
     */
    @PostMapping(value = "/modifyScooter", params = "Delete")
    public String deleteScooter(@ModelAttribute("scooter") Scooter scooter) {
        this.scooterService.deleteScooter(scooter.getDocumentName());
        return "redirect:/scooters";
    }
}
