package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.services.IdentityService;
import com.sustech.flightbooking.viewmodel.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController extends ControllerBase {

    private final IdentityService identityService;


    @Autowired
    public HomeController(IdentityService identityService) {
        this.identityService = identityService;
    }


    @GetMapping("/")
    public ModelAndView index() {
        return page("index");
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(value = "returnUri", required = false) String returnUri) {
        LoginViewModel viewModel = new LoginViewModel();
        viewModel.setReturnUri(returnUri);
        return pageWithViewModel("login", viewModel);
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginViewModel model) {
        FlightBookingAuthenticationToken token = identityService.login(model.getUserName(), model.getPassword());
        if (token != null) {
            String returnUri = model.getReturnUri().isEmpty() ?
                    ("/" + (token.getRole().equalsIgnoreCase("passenger") ? "passenger" : "manage"))
                    : model.getReturnUri();
            return redirect(returnUri);
        }
        //clear password
        model.setPassword("");
        return pageWithViewModel("login", model);
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        identityService.logout();
        return redirect("/");
    }

    @GetMapping("/manage")
    public ModelAndView adminIndex() {
        return page("admin/index");
    }
}
