package com.sustech.flightbooking.controllers.manage;


import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.persistence.FlightRepository;
import com.sustech.flightbooking.services.FlightService;
import com.sustech.flightbooking.viewmodel.flights.CreateFlightViewModel;
import com.sustech.flightbooking.viewmodel.flights.FlightViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("manage/flights")
public class FlightsManagementController extends ControllerBase {

    private final FlightRepository flightRepository;
    private final FlightService flightService;

    @Autowired
    public FlightsManagementController(FlightRepository flightRepository, FlightService flightService) {
        this.flightRepository = flightRepository;
        this.flightService = flightService;
    }


    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView modelAndView = page("admin/flights/list");
        List<FlightViewModel> flightViewModels = flightRepository.findAll().stream()
                .map(flight -> {
                    FlightViewModel vm = new FlightViewModel();

                    vm.setId(flight.getId());
                    vm.setFlightId(flight.getFlightId());
                    vm.setPrice(flight.getPrice());
                    vm.setOrigin(flight.getOrigin());
                    vm.setDestination(flight.getDestination());
                    vm.setDepartureTime(flight.getDepartureTime());
                    vm.setArrivalTime(flight.getArrivalTime());
                    vm.setCapacity(flight.getCapacity());
                    vm.setStatus(flightService.getStatus(flight));
                    vm.setOrderCount(flightService.getOrders(flight).size());
                    return vm;
                })
                .collect(Collectors.toList());
        modelAndView.getModelMap().put("flights", flightViewModels);
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView createFlight() {
        return pageWithViewModel("admin/flights/create",
                new CreateFlightViewModel());
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute CreateFlightViewModel model) {
        Flight flight = new Flight(UUID.randomUUID(),
                model.getFlightId(),
                model.getPrice(),
                model.getOrigin(),
                model.getDestination(),
                model.getDepartureTime(),
                model.getArrivalTime(),
                model.getCapacity());
        List<String> errorMessages = flightService.validate(flight);
        if (!model.getDepartureTime().isAfter(LocalDateTime.now().plusHours(2))) {
            errorMessages.add("Departure time must at least 2 hours after.");
        }
        if (errorMessages.size() > 0) {
            //show error message
        }
        if (model.isPublishNow()) {
            flight.publish();
        }
        flightRepository.save(flight);
        return redirect("/manage/flights");
    }
}
