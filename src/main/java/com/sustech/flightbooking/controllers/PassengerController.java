package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.services.PassengerService;
import com.sustech.flightbooking.viewmodel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * Created by Henry on 4/23/2017.
 */

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        Passenger p = new Passenger(UUID.randomUUID());
        p.setUserName("aaa");


        model.addAttribute("model", new LoginViewModel());
        return "passengers/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginViewModel model) {
        if (passengerService.login(model.getUserName(), model.getPassword())) {
            //set cookie here

        }
        return "";
    }
}
