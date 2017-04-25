package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.config.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.services.IdentityService;
import com.sustech.flightbooking.viewmodel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by Henry on 4/16/2017.
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IdentityService service;

    @Autowired
    private PassengerRepository repo;

    @GetMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.put("now", new Date());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("model", new LoginViewModel());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginViewModel model) {
        FlightBookingAuthenticationToken token = service.login(model.getUserName(), model.getPassword());
        if (token != null)
            return String.format("redirect:/%s", token.getRole());
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        service.logout();
        return "redirect:/";
    }
}
