package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.services.IdentityService;
import com.sustech.flightbooking.viewmodel.LoginViewModel;
import com.sustech.flightbooking.viewmodel.RegisterPassengerViewModel;
import com.sustech.flightbooking.viewmodel.ViewModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class HomeController extends ControllerBase {

    private final IdentityService identityService;
    private final PassengerRepository passengerRepository;


    @Autowired
    public HomeController(IdentityService identityService, PassengerRepository passengerRepository) {
        this.identityService = identityService;
        this.passengerRepository = passengerRepository;
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
        ModelAndView modelAndView = pageWithViewModel("login", model);
        modelAndView.getModelMap().put("errorMessages", errorMessages("Invalid user name or password"));
        return modelAndView;
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

    @GetMapping("register")
    public ModelAndView registerPage() {
        return pageWithViewModel("register",
                new RegisterPassengerViewModel());
    }

    @PostMapping("register")
    public ModelAndView register(@ModelAttribute RegisterPassengerViewModel model) {
        List<String> errorMessages = ViewModelValidator.validate(model);
        if (passengerRepository.findByUserName(model.getUserName()) != null) {
            errorMessages.add("User name already exists.");
        }
        if (passengerRepository.findByIdCard(model.getIdentityNumber()) != null) {
            errorMessages.add("ID card is already registered.");
        }
        if (errorMessages.size() > 0) {
            ModelAndView modelAndView = pageWithViewModel("register", model);
            modelAndView.getModelMap().put("errorMessages", errorMessages);
            return modelAndView;
        }
        Passenger passenger = new Passenger(UUID.randomUUID());

        passenger.setUserName(model.getUserName());
        passenger.setDisplayName(model.getDisplayName());
        passenger.setIdentityCardNumber(model.getIdentityNumber());

        passengerRepository.save(passenger);
        return redirect("/login");
    }
}
