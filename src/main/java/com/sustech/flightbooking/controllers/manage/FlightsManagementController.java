package com.sustech.flightbooking.controllers.manage;


import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.misc.SearchInfos;
import com.sustech.flightbooking.misc.responseHandling.ErrorMessageHandler;
import com.sustech.flightbooking.persistence.FlightRepository;
import com.sustech.flightbooking.services.FlightService;
import com.sustech.flightbooking.viewmodel.manage.OrderAdminViewModel;
import com.sustech.flightbooking.viewmodel.manage.flights.CreateEditFlightViewModel;
import com.sustech.flightbooking.viewmodel.manage.flights.FlightDetailViewModel;
import com.sustech.flightbooking.viewmodel.manage.flights.FlightEditViewModel;
import com.sustech.flightbooking.viewmodel.manage.flights.FlightListViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("manage/flights")
public class FlightsManagementController extends ControllerBase {

    private final FlightRepository flightRepository;
    private final FlightService flightService;

    @Autowired
    public FlightsManagementController(FlightRepository flightRepository,
                                       FlightService flightService) {
        this.flightRepository = flightRepository;
        this.flightService = flightService;
    }


    @GetMapping("")
    public ModelAndView query(@RequestParam(value = "city", required = false) String city,
                              @RequestParam(value = "flightNumber", required = false) String flightNumber,
                              @RequestParam(value = "date", required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ModelAndView modelAndView = page("admin/flights/list");
        List<FlightListViewModel> flightListViewModels =
                flightService.search(city, flightNumber, date)
                        .filter(f -> !f.isDeleted())
                        .map(this::mapFlightToViewModel)
                        .collect(Collectors.toList());
        SearchInfos.addToModelAndView(modelAndView, city, flightNumber, date);
        modelAndView.getModelMap().put("flights", flightListViewModels);
        return modelAndView;
    }

    private FlightListViewModel mapFlightToViewModel(Flight flight) {
        FlightListViewModel vm = new FlightListViewModel();
        vm.setId(flight.getId());
        vm.setFlightNumber(flight.getFlightNumber());
        vm.setPrice(flight.getPrice());
        vm.setOrigin(flight.getOrigin());
        vm.setDestination(flight.getDestination());
        vm.setDepartureTime(flight.getDepartureTime());
        vm.setArrivalTime(flight.getArrivalTime());
        vm.setCapacity(flight.getCapacity());
        vm.setStatus(flightService.getStatus(flight));
        vm.setOrderCount(flightService.getOrders(flight).size());
        return vm;
    }

    @GetMapping("create")
    public ModelAndView createFlight() {
        return pageWithViewModel("admin/flights/create",
                new CreateEditFlightViewModel());
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute CreateEditFlightViewModel model) {
        return createOrUpdateFlight(model, UUID.randomUUID(), "admin/flights/create");
    }

    @GetMapping("{id}/edit")
    public ModelAndView editPage(@PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            return notFound();
        }
        switch (flightService.getStatus(flight)) {
            case UNPUBLISHED:
                return prePublishEditPage(flight);
            case FULL:
            case AVAILABLE:
                return editPage(flight);
            default:
                return notFound();
        }
    }

    private ModelAndView editPage(Flight flight) {
        FlightEditViewModel vm = new FlightEditViewModel();
        vm.setCapacity(flight.getCapacity());
        vm.setPrice(flight.getPrice());
        ModelAndView modelAndView = pageWithViewModel("admin/flights/edit", vm);
        modelAndView.getModelMap().put("flightId", flight.getId());
        return modelAndView;
    }

    private ModelAndView prePublishEditPage(Flight flight) {
        CreateEditFlightViewModel vm = mapFlightToCreateEditModel(flight);
        ModelAndView modelAndView = pageWithViewModel("admin/flights/prepub-edit", vm);
        modelAndView.getModelMap().put("flightId", flight.getId());
        return modelAndView;
    }

    private CreateEditFlightViewModel mapFlightToCreateEditModel(Flight flight) {
        CreateEditFlightViewModel vm = new CreateEditFlightViewModel();
        vm.setFlightNumber(flight.getFlightNumber());
        vm.setOrigin(flight.getOrigin());
        vm.setDestination(flight.getDestination());
        vm.setPrice(flight.getPrice());
        vm.setCapacity(flight.getCapacity());
        vm.setDepartureTime(flight.getDepartureTime());
        vm.setArrivalTime(flight.getArrivalTime());
        return vm;
    }

    @PostMapping("{id}/prepubupdate")
    public ModelAndView prePubUpdate(@ModelAttribute CreateEditFlightViewModel model, @PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null || flightService.getStatus(flight) != FlightStatus.UNPUBLISHED) {
            return notFound();
        }
        return createOrUpdateFlight(model, id, "admin/flights/prepub-edit");
    }

    @PostMapping("{id}/update")
    public ModelAndView update(@ModelAttribute FlightEditViewModel model, @PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null ||
                (flightService.getStatus(flight) != FlightStatus.AVAILABLE
                        && flightService.getStatus(flight) != FlightStatus.FULL)) {
            return notFound();
        }
        flight.setCapacity(model.getCapacity());
        flight.setPrice(model.getPrice());
        List<String> errorMessages = flightService.validate(flight);
        return ErrorMessageHandler.fromViewModel(model, "admin/flights/edit")
                .putModelMap("flightId", id)
                .addErrorMessages(errorMessages)
                .onSuccess(() -> {
                    flightRepository.save(flight);
                    return redirect("/manage/flights");
                })
                .result();
    }

    private ModelAndView createOrUpdateFlight(CreateEditFlightViewModel model, UUID id, String viewName) {
        Flight flight = mapCreateEditModelToFlight(model, id);
        List<String> errorMessages = flightService.validate(flight);
        return ErrorMessageHandler.fromViewModel(model, viewName)
                .putModelMap("flightId", id)
                .addErrorMessages(errorMessages)
                .onSuccess(() -> {
                    if (model.isPublishNow()) {
                        flight.publish();
                    }
                    flightRepository.save(flight);
                    return redirect("/manage/flights");
                })
                .result();
    }

    private Flight mapCreateEditModelToFlight(CreateEditFlightViewModel model, UUID id) {
        return new Flight(id,
                model.getFlightNumber(),
                model.getPrice(),
                model.getOrigin(),
                model.getDestination(),
                model.getDepartureTime(),
                model.getArrivalTime(),
                model.getCapacity());
    }

    @GetMapping("{id}")
    public ModelAndView detail(@PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            return notFound();
        }
        FlightDetailViewModel vm = mapFlightToDetailViewModel(flight);
        return pageWithViewModel("admin/flights/detail", vm);
    }

    private FlightDetailViewModel mapFlightToDetailViewModel(Flight flight) {
        FlightDetailViewModel vm = new FlightDetailViewModel();

        vm.setId(flight.getId());
        vm.setFlightNumber(flight.getFlightNumber());
        vm.setPrice(flight.getPrice());
        vm.setOrigin(flight.getOrigin());
        vm.setDestination(flight.getDestination());
        vm.setDepartureTime(flight.getDepartureTime());
        vm.setArrivalTime(flight.getArrivalTime());
        vm.setCapacity(flight.getCapacity());
        vm.setStatus(flightService.getStatus(flight));
        vm.setOrderCount(flightService.getOrders(flight).size());

        List<OrderAdminViewModel> orders = flightService.getOrders(flight).stream()
                .map(OrderAdminViewModel::createFromDomainModel)
                .collect(Collectors.toList());
        vm.setOrders(orders);
        return vm;
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
        return redirect(String.format("/manage/flights/%s", id));
    }

    @GetMapping("{id}/delete")
    public ModelAndView delete(@PathVariable UUID id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            return notFound();
        }
        switch (flightService.getStatus(flight)) {
            case UNPUBLISHED:
            case TERMINATE:
                flight.delete();
                flightRepository.save(flight);
                return redirect("/manage/flights");
            default:
                return badRequest("Current state does not allow deleting.");
        }

    }
}
