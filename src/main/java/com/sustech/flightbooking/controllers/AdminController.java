package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.viewmodel.CreateAdminViewModel;
import com.sustech.flightbooking.viewmodel.CreatePassengerViewModel;
import com.sustech.flightbooking.viewmodel.EditPassengerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.spring4.view.ThymeleafView;

import java.util.UUID;

@Controller
@RequestMapping("/manage")
public class AdminController {

    private final PassengerRepository passengerRepository;

    @Autowired
    public AdminController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }

    @GetMapping("passengers")
    public ModelAndView passengers() {
        ModelAndView modelAndView = new ModelAndView("admin/passengers/list");
        modelAndView.getModel().put("passengers", passengerRepository.findAll());
        return modelAndView;
    }

    @GetMapping("passengers/create")
    public ModelAndView createPassenger() {
        ModelAndView modelAndView = new ModelAndView("admin/passengers/create");
        modelAndView.getModelMap().put("model", new CreatePassengerViewModel());
        return modelAndView;
    }

    @GetMapping("passengers/{id}/edit")
    public ModelAndView editPassenger(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("admin/passengers/edit");
        Passenger passenger = passengerRepository.findById(id);
        if (passenger == null) {
            View view = new ThymeleafView("/error/404.html");
            modelAndView.setView(view);
        } else {
            EditPassengerViewModel vm = new EditPassengerViewModel();
            vm.setDisplayName(passenger.getDisplayName());
            vm.setUserName(passenger.getUserName());
            vm.setIdentityNumber(passenger.getIdentityCardNumber());
            modelAndView.getModelMap().put("model", vm);
            modelAndView.getModel().put("passengerId", passenger.getId());
        }
        return modelAndView;
    }

    @PostMapping("passengers/{id}/update")
    public View updatePassenger(@ModelAttribute EditPassengerViewModel model, @PathVariable UUID id) {
        Passenger passenger = passengerRepository.findById(id);
        if (passenger == null) {
            return new ThymeleafView("/error/404.html");
        }
        passenger.setUserName(model.getUserName());
        passenger.setDisplayName(model.getDisplayName());
        passenger.setIdentityCardNumber(model.getIdentityNumber());

        passengerRepository.save(passenger);
        return new RedirectView("/manage/passengers");
    }

    @GetMapping("admins/create")
    public ModelAndView createAdmin() {
        ModelAndView modelAndView = new ModelAndView("admin/admins/create");
        modelAndView.getModelMap().put("model", new CreateAdminViewModel());
        return modelAndView;
    }
}
