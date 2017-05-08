package com.sustech.flightbooking.controllers.manage;


import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.persistence.FlightRepository;
import com.sustech.flightbooking.services.FlightService;
import com.sustech.flightbooking.viewmodel.flights.CreateFlightViewModel;
import com.sustech.flightbooking.viewmodel.flights.FlightDetailViewModel;
import com.sustech.flightbooking.viewmodel.flights.FlightViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
                .filter(flight -> !flight.isDeleted())
                .map(flight -> {
                    FlightViewModel vm = new FlightViewModel();

                    vm.setId(flight.getId());
                    vm.setFlightId(flight.getFlightNumber());
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


    //not implemented yet
    @GetMapping("{id}")
    public ModelAndView detail(@PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            return notFound();
        }
        return pageWithViewModel("admin/flights/detail", new FlightDetailViewModel());
    }


    @GetMapping("{id}/publish")
    public ModelAndView publish(@PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            return notFound();
        }
        if (flightService.getStatus(flight) != FlightStatus.UNPUBLISHED) {
            return badRequest("Current state does not allow publishing.");
        }
        flight.publish();
        flightRepository.save(flight);
        return redirect(String.format("manage/flights/%s", id));
    }

    @GetMapping("{id}/delete")
    public ModelAndView delete(@PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            return notFound();
        }
        FlightStatus status = flightService.getStatus(flight);
        if (status != FlightStatus.UNPUBLISHED
                && status != FlightStatus.TERMINATE) {
            return badRequest("Current state does not allow deleting.");
        }
        flight.delete();
        flightRepository.save(flight);
        return redirect("manage/flights");
    }
}
