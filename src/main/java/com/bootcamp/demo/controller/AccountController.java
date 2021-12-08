package com.bootcamp.demo.controller;

import com.bootcamp.demo.model.User;
import com.bootcamp.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registerUser";
    }

    @PostMapping("/register")
    public String submitForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model m) {
        if (!bindingResult.hasErrors()) {
            try {
                this.userService.saveUser(user);
                m.addAttribute("message", "Successfully registered...");
                return "redirect:/login";
            } catch (RuntimeException re) {
                m.addAttribute("alreadyExists", re.getMessage());
            }
        }
        return "registerUser";
    }
}
