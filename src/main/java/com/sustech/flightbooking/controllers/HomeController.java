package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.misc.SearchInfos;
import com.sustech.flightbooking.services.FlightService;
import com.sustech.flightbooking.viewmodel.passenger.flight.AvailableFlightListViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController extends ControllerBase {

    private final FlightService flightService;


    @Autowired
    public HomeController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return page("index");
    }

    @GetMapping("/manage")
    public ModelAndView adminHome() {
        return page("admin/index");
    }

    @GetMapping("/passenger")
    public ModelAndView passengerHome() {
        return page("passenger/index");
    }

    @GetMapping("flights")
    public ModelAndView showAvailableFlights(@RequestParam(value = "city", required = false) String city,
                                             @RequestParam(value = "flightNumber", required = false) String flightNumber,
                                             @RequestParam(value = "date", required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AvailableFlightListViewModel> searchResult =
                flightService.search(city, flightNumber, date)
                        .filter(flight -> flightService.getStatus(flight) == FlightStatus.AVAILABLE)
                        .map(this::mapFlightToAvailableFlightListViewModel)
                        .collect(Collectors.toList());
        ModelAndView modelAndView = page("availableFlights");
        modelAndView.getModelMap().put("flights", searchResult);
        SearchInfos.addToModelAndView(modelAndView, city, flightNumber, date);
        return modelAndView;
    }

    private AvailableFlightListViewModel mapFlightToAvailableFlightListViewModel(Flight flight) {
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
    }

}
