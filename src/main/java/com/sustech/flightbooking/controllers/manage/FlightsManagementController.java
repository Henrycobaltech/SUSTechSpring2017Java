package com.sustech.flightbooking.controllers.manage;


import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.persistence.FlightRepository;
import com.sustech.flightbooking.services.FlightService;
import com.sustech.flightbooking.viewmodel.CreateFlightViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

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
