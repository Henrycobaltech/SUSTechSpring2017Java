package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.services.IdentityService;
import com.sustech.flightbooking.viewmodel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Henry on 4/16/2017.
 */

@Controller
@RequestMapping("/")
public class HomeController {

    private final IdentityService identityService;


    @Autowired
    public HomeController(IdentityService identityService) {
        this.identityService = identityService;
    }


    @GetMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.put("now", new Date());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "returnUri", required = false) String returnUri) {
        LoginViewModel viewModel = new LoginViewModel();
        viewModel.setReturnUri(returnUri);
        model.addAttribute("model", viewModel);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginViewModel model) {
        FlightBookingAuthenticationToken token = identityService.login(model.getUserName(), model.getPassword());
        if (token != null) {
            String returnUri = model.getReturnUri();
            return String.format("redirect:%s", returnUri.isEmpty() ? ("/" + token.getRole()) : returnUri);
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        identityService.logout();
        return "redirect:/";
    }
}
