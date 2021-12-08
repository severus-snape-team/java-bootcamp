package com.bootcamp.demo.controller;

import com.bootcamp.demo.controller.qrcode.QRCodeGenerator;
import com.bootcamp.demo.model.Repair;
import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.service.ScooterService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.lang.System.getProperty;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("")
public class ScooterController {

    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping("/admin/createScooter")
    public String register(Model model) {
        Scooter scooter = new Scooter();
        model.addAttribute("scooter", scooter);
        model.addAttribute("mapsApiKey", getProperty("mapsKey"));
        return "createScooterForm";
    }

    @PostMapping("/admin/createScooter")
    public String submitForm(@Valid @ModelAttribute("scooter") Scooter scooter, BindingResult bindingResult, Model m) {
        if (!bindingResult.hasErrors()) {
            this.scooterService.insertScooter(scooter);
            m.addAttribute("message", "Successfully added...");
        }
        m.addAttribute("mapsApiKey", getProperty("mapsKey"));
        return "createScooterForm";
    }

    /**
     * Returning an html page with all the scooters listed (without details)
     */
    @GetMapping("/admin/scooters")
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
    @GetMapping("/admin/scooters/{scooterName}")
    public String viewScooter(@PathVariable String scooterName, Model model) {
        try {
            byte[] image = QRCodeGenerator.getQRCodeImage("https://java-bootcamp.herokuapp.com/admin/scooters/" + scooterName, 180, 180);
            String qrcode = Base64.getEncoder().encodeToString(image);
            model.addAttribute("qr_code_img", qrcode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        Scooter scooter = scooterService.getScooterByName(scooterName);
        if (scooter == null)
            return "redirect:/admin/scooters";
        model.addAttribute("scooter", scooter);
        model.addAttribute("mapsApiKey", getProperty("mapsKey"));
        return "modifyScooterForm";
    }


    /**
     * Updates the scooter received from model if the "Update" button was pressed on getScooter page
     */
    @PostMapping(value = "/admin/modifyScooter", params = "Update")
    public String updateScooter(@Valid @ModelAttribute("scooter") Scooter scooter, BindingResult bindingResult, Model m) {
        try {
            byte[] image = QRCodeGenerator.getQRCodeImage("https://java-bootcamp.herokuapp.com/admin/scooters/" + scooter.getDocumentName(), 180, 180);
            String qrcode = Base64.getEncoder().encodeToString(image);
            m.addAttribute("qr_code_img", qrcode);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        if (!bindingResult.hasErrors()) {
            this.scooterService.insertScooter(scooter);
            m.addAttribute("message", "Successfully updated...");
        }
        m.addAttribute("mapsApiKey", getProperty("mapsKey"));
        return "modifyScooterForm";
    }

    /**
     * Deletes the scooter received from model if the "Delete" button was pressed on getScooter page
     */
    @PostMapping(value = "/admin/modifyScooter", params = "Delete")
    public String deleteScooter(@ModelAttribute("scooter") Scooter scooter) {
        this.scooterService.deleteScooter(scooter.getDocumentName());
        return "redirect:/admin/scooters";
    }


    @GetMapping("")
    public String index(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("mapsApiKey", getProperty("mapsKey"));
        model.addAttribute("scooters", this.scooterService.returnAllScooters());
        model.addAttribute("states", this.scooterService.returnNumberStates());
        return "index";
    }
    @GetMapping("/admin")
    public String indexAdmin(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("mapsApiKey", getProperty("mapsKey"));
        model.addAttribute("scooters", this.scooterService.returnAllScooters());
        model.addAttribute("states", this.scooterService.returnNumberStates());
        return "index";
    }

    @GetMapping("/admin/repair/{scooterName}")
    public String registerReparation(@PathVariable String scooterName, Model model){
        Scooter scooter = scooterService.getScooterByName(scooterName);
        Repair repair = new Repair();
        repair.setScooterDoc(scooter.getDocumentName());
        model.addAttribute("repair", repair);
        return "reparations";
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @PostMapping(value = "/admin/addRepair", params = "AddRepair")
    public String submitReparation(@ModelAttribute("repair") Repair repair){
        this.scooterService.insertReparation(repair.getScooterDoc(), repair);
        return "redirect:/admin/repair";
    }

    @GetMapping("/admin/repairs/{scooterName}")
    public String viewAllRepairs(@PathVariable String scooterName, Model model) throws ExecutionException, InterruptedException{
        Scooter scooter = scooterService.getScooterByName(scooterName);
        model.addAttribute("scooter", scooter);
        model.addAttribute("repairs", scooter.getRepairs());
        return "listRepairs";
    }
}
