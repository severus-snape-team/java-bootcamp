package com.bootcamp.demo.controller;

import com.bootcamp.demo.controller.qrcode.QRCodeGenerator;
import com.bootcamp.demo.model.Scooter;
import com.bootcamp.demo.model.ScooterRental;
import com.bootcamp.demo.service.RentalService;
import com.bootcamp.demo.service.ScooterService;
import com.bootcamp.demo.service.UserService;
import com.google.zxing.WriterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Controller
@RequestMapping("/user")
public class RentalController {

    private final ScooterService scooterService;
    private final RentalService rentalService;
    private final UserService userService;

    public RentalController(ScooterService scooterService, RentalService rentalService, UserService userService) {
        this.scooterService = scooterService;
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @GetMapping("/rentalScooters")
    public String viewAllScooters(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("scooters", this.scooterService.returnAvailableScooters());
        return "listAvailableScooters";
    }

    @GetMapping("/rentalScooters/{scooterName}")
    public String viewScooter(@PathVariable String scooterName, Model model) {
        try {
            byte[] image = QRCodeGenerator.getQRCodeImage("https://java-bootcamp.herokuapp.com/user/rentalScooters/" + scooterName, 180, 180);
            String qrcode = Base64.getEncoder().encodeToString(image);
            model.addAttribute("qr_code_img", qrcode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        Scooter scooter = scooterService.getScooterByName(scooterName);
        if (scooter == null)
            return "redirect:/user/rentalScooters";
        ScooterRental scooterRental = rentalService.getRentalByName(scooterName);
        if (scooterRental != null) {
            BigDecimal cost = rentalService.getRentalCost(scooterName);
            long time = rentalService.getTimeRental(scooterName);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String startDate = formatter.format(rentalService.getRentalByName(scooterName).getStartDate());
            model.addAttribute("rentalCost", cost);
            model.addAttribute("rentalTime", time);
            model.addAttribute("startDate", startDate);
        }
        model.addAttribute("scooter", scooter);
        model.addAttribute("userName", userService.getUserByEmail(getContext().getAuthentication().getPrincipal().toString()).getName());
        return "getScooterDetails";
    }

    @GetMapping(value = "/rentScooter", params = "Rent")
    public String getRentScooter(@ModelAttribute("scooter") Scooter scooter, Model m) {
        try {
            byte[] image = QRCodeGenerator.getQRCodeImage("https://java-bootcamp.herokuapp.com/user/rentalScooters/" + scooter.getDocumentName(), 180, 180);
            String qrcode = Base64.getEncoder().encodeToString(image);
            m.addAttribute("qr_code_img", qrcode);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        m.addAttribute("message", "Successfully rented...");
        return "redirect:/user/rentalScooters/" + scooter.getDocumentName();
    }

    @PostMapping(value = "/rentScooter", params = "Rent")
    public String rentScooter(@ModelAttribute("scooter") Scooter scooter, Model m) {
        try {
            byte[] image = QRCodeGenerator.getQRCodeImage("https://java-bootcamp.herokuapp.com/user/rentalScooters/" + scooter.getDocumentName(), 180, 180);
            String qrcode = Base64.getEncoder().encodeToString(image);
            m.addAttribute("qr_code_img", qrcode);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        this.rentalService.saveRental(scooter.getDocumentName());
        m.addAttribute("message", "Successfully rented...");
        scooter = this.scooterService.getScooterByName(scooter.getDocumentName());
//        return "getScooterDetails";
        return getRentScooter(scooter, m);
    }

    @GetMapping(value = "/rentScooter", params = "Stop")
    public String getStopRental(@ModelAttribute("scooter") Scooter scooter) {
        return "redirect:/user/rentalScooters";
    }

    @PostMapping(value = "/rentScooter", params = "Stop")
    public String stopRental(@ModelAttribute("scooter") Scooter scooter) {
        this.rentalService.deleteRental(scooter.getDocumentName());
        scooter = this.scooterService.getScooterByName(scooter.getDocumentName());
        return getStopRental(scooter);
    }

    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @GetMapping("/sse/{scooterName}")
    public SseEmitter handleSse(@PathVariable String scooterName, Model model) {
        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                try {
                    //model.addAttribute("show_stop_btn","show_stop_btn");
                    emitter.send(this.rentalService.getRentalCost(scooterName));
                } catch (Exception ignored) {
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }
}
