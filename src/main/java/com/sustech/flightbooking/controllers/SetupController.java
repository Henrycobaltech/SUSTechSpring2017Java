package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.services.SetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SetupController extends ControllerBase {

    private final SetupService setupService;

    @Autowired
    public SetupController(SetupService setupService) {
        this.setupService = setupService;
    }

    @GetMapping("/setup")
    public ModelAndView setupPage() {
        if (setupService.isSetup()) {
            return redirect("/");
        }
        return page("setup");
    }

    @PostMapping("/setup")
    public ModelAndView setup(@ModelAttribute(name = "createFlights") boolean createFlights) {
        if (setupService.isSetup()) {
            return badRequest("System is already setup.");
        }
        setupService.setup(createFlights);
        return redirect("/");
    }
}
