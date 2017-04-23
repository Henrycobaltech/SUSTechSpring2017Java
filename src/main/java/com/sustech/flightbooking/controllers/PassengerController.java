package com.sustech.flightbooking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Henry on 4/23/2017.
 */

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    @GetMapping("/login")
    public String login() {
        return "passengers/login";
    }
}
