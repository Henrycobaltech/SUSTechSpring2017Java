package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.viewmodel.RegisterPassengerViewModel;
import com.sustech.flightbooking.viewmodel.manage.passengers.EditPassengerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/manage/passengers")
public class PassengerManagementController extends ControllerBase {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerManagementController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView modelAndView = page("admin/passengers/list");
        modelAndView.getModelMap().put("passengers", passengerRepository.findAll());
        return modelAndView;
    }

    @GetMapping("{id}/edit")
    public ModelAndView editPage(@PathVariable UUID id) {
        Passenger passenger = passengerRepository.findById(id);
        if (passenger == null) {
            return notFound();
        } else {
            EditPassengerViewModel vm = new EditPassengerViewModel();

            vm.setDisplayName(passenger.getDisplayName());
            vm.setUserName(passenger.getUserName());
            vm.setIdentityNumber(passenger.getIdentityCardNumber());

            ModelAndView modelAndView = pageWithViewModel("admin/passengers/edit", vm);
            modelAndView.getModelMap().put("passengerId", passenger.getId());
            return modelAndView;
        }
    }

    @PostMapping("{id}/update")
    public ModelAndView update(@ModelAttribute RegisterPassengerViewModel model, @PathVariable UUID id) {
        Passenger passenger = passengerRepository.findById(id);
        if (passenger == null) {
            return notFound();
        }
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            // password does not match
        }
        passenger.setUserName(model.getUserName());
        passenger.setDisplayName(model.getDisplayName());
        passenger.setIdentityCardNumber(model.getIdentityNumber());
        passenger.setPassword(model.getPassword());

        passengerRepository.save(passenger);
        return redirect("/manage/passengers");
    }
}
