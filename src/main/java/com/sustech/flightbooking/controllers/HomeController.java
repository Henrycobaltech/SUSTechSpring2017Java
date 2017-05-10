package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.persistence.FlightRepository;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.services.FlightService;
import com.sustech.flightbooking.services.IdentityService;
import com.sustech.flightbooking.viewmodel.LoginViewModel;
import com.sustech.flightbooking.viewmodel.PassengerEditModelViewModel;
import com.sustech.flightbooking.viewmodel.ViewModelValidator;
import com.sustech.flightbooking.viewmodel.passenger.flight.AvailableFlightListViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController extends ControllerBase {

    private final IdentityService identityService;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;


    @Autowired
    public HomeController(IdentityService identityService, PassengerRepository passengerRepository,
                          FlightRepository flightRepository, FlightService flightService) {
        this.identityService = identityService;
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.flightService = flightService;
    }


    @GetMapping("/")
    public ModelAndView index() {
        return page("index");
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(value = "returnUri", required = false) String returnUri) {
        LoginViewModel viewModel = new LoginViewModel();
        viewModel.setReturnUri(returnUri);
        if (identityService.getCurrentUser() != null && !returnUri.isEmpty()) {
            return pageWithErrorMessages("login", viewModel, errorMessages("You may not have permission accessing that page."));
        }
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
        return pageWithErrorMessages("login", model, errorMessages("Invalid user name or password"));
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
                new PassengerEditModelViewModel());
    }

    @PostMapping("register")
    public ModelAndView register(@ModelAttribute PassengerEditModelViewModel model) {
        List<String> errorMessages = ViewModelValidator.validate(model);
        if (passengerRepository.findByUserName(model.getUserName()) != null) {
            errorMessages.add("User name already exists.");
        }
        if (passengerRepository.findByIdCard(model.getIdentityNumber()) != null) {
            errorMessages.add("ID card is already registered.");
        }
        if (errorMessages.size() > 0) {
            return pageWithErrorMessages("register", model, errorMessages);
        }
        Passenger passenger = new Passenger(UUID.randomUUID());

        passenger.setUserName(model.getUserName());
        passenger.setDisplayName(model.getDisplayName());
        passenger.setIdentityCardNumber(model.getIdentityNumber());

        passengerRepository.save(passenger);
        return redirect("/login");
    }

    @GetMapping("flights")
    public ModelAndView showAvailableFlights() {
        List<AvailableFlightListViewModel> availableFlights = flightRepository.findAll().stream()
                .filter(flight -> flightService.getStatus(flight) == FlightStatus.AVAILABLE)
                .map(flight -> {
                    AvailableFlightListViewModel vm = new AvailableFlightListViewModel();

                    vm.setId(flight.getId());
                    vm.setFlightNumber(flight.getFlightNumber());
                    vm.setPrice(flight.getPrice());
                    vm.setOrigin(flight.getOrigin());
                    vm.setDestination(flight.getDestination());
                    vm.setDepartureTime(flight.getDepartureTime());
                    vm.setArrivalTime(flight.getArrivalTime());
                    vm.setRemainingSeatsCount(flight.getCapacity() - flightService.getOrders(flight).size());

                    return vm;
                })
                .collect(Collectors.toList());
        ModelAndView modelAndView = page("availableFlights");
        modelAndView.getModelMap().put("flights", availableFlights);
        return modelAndView;
    }
}
