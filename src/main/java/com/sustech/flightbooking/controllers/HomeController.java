package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.services.IdentityService;
import com.sustech.flightbooking.viewmodel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.spring4.view.ThymeleafView;

import java.util.Date;

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
    public ModelAndView login(ModelAndView modelAndView,
                              @RequestParam(value = "returnUri", required = false) String returnUri) {
        LoginViewModel viewModel = new LoginViewModel();
        viewModel.setReturnUri(returnUri);
        modelAndView.getModelMap().put("model", viewModel);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public View login(@ModelAttribute LoginViewModel model) {
        FlightBookingAuthenticationToken token = identityService.login(model.getUserName(), model.getPassword());
        if (token != null) {
            String returnUri = model.getReturnUri().isEmpty() ?
                    ("/" + (token.getRole().equalsIgnoreCase("passenger") ? "passenger" : "manage"))
                    : model.getReturnUri();
            RedirectView view = new RedirectView(returnUri);
            view.setExposeModelAttributes(false);
            return view;
        }
        return new ModelAndView("login").getView();
    }

    @GetMapping("/logout")
    public View logout() {
        identityService.logout();
        RedirectView view = new RedirectView("/");
        view.setExposeModelAttributes(false);
        return view;
    }
}
