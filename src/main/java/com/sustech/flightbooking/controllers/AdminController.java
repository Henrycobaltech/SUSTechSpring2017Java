package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.ThymeleafView;

import java.util.UUID;

@Controller
@RequestMapping("/manage")
public class AdminController extends ControllerBase {

    private final PassengerRepository passengerRepository;
    private final AdministratorsRepository adminsRepository;

    @Autowired
    public AdminController(PassengerRepository passengerRepository, AdministratorsRepository adminsRepository) {
        this.passengerRepository = passengerRepository;
        this.adminsRepository = adminsRepository;
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

    @PostMapping("passengers/create")
    public View createPassenger(@ModelAttribute CreatePassengerViewModel model, @PathVariable UUID id) {
        Passenger passenger = passengerRepository.findById(id);
        if (passenger == null) {
            return new ThymeleafView("/error/404.html");
        }
        passenger.setUserName(model.getUserName());
        passenger.setDisplayName(model.getDisplayName());
        passenger.setIdentityCardNumber(model.getIdentityNumber());

        passengerRepository.save(passenger);
        return redirect("/manage/passengers");
    }

    @PostMapping("passengers/{id}/update")
    public View updatePassenger(@ModelAttribute CreatePassengerViewModel model, @PathVariable UUID id) {
        Passenger passenger = passengerRepository.findById(id);
        if (passenger == null) {
            return new ThymeleafView("/error/404.html");
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

    @GetMapping("admins")
    public ModelAndView admins() {
        ModelAndView modelAndView = new ModelAndView("admin/admins/list");
        modelAndView.getModel().put("admins", adminsRepository.findAll());
        return modelAndView;
    }

    @GetMapping("admins/create")
    public ModelAndView createAdmin() {
        ModelAndView modelAndView = new ModelAndView("admin/admins/create");
        modelAndView.getModelMap().put("model", new CreateAdminViewModel());
        return modelAndView;
    }

    @GetMapping("admins/{id}/edit")
    public ModelAndView editAdmin(@PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("admin/admins/edit");
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            View view = new ThymeleafView("/error/404.html");
            modelAndView.setView(view);
        } else {
            EditAdminViewModel vm = new EditAdminViewModel();
            vm.setUserName(admin.getUserName());
            modelAndView.getModelMap().put("model", vm);
            modelAndView.getModel().put("adminId", admin.getId());
        }
        return modelAndView;
    }

    @PostMapping("admins/{id}/update")
    public View createAdmin(@ModelAttribute CreateAdminViewModel model, @PathVariable UUID id) {
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            return new ThymeleafView("/error/404.html");
        }
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            // password does not match
        }
        admin.setUserName(model.getUserName());
        admin.setPassword(model.getPassword());

        adminsRepository.save(admin);
        return redirect("/manage/admins");
    }

    @PostMapping("admins/{id}/update")
    public View updateAdmin(@ModelAttribute EditAdminViewModel model, @PathVariable UUID id) {
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            return new ThymeleafView("/error/404.html");
        }
        admin.setUserName(model.getUserName());

        adminsRepository.save(admin);
        return redirect("/manage/admins");
    }

    @GetMapping("flights/create")
    public ModelAndView createFlight() {
        ModelAndView modelAndView = new ModelAndView("admin/flights/create");
        modelAndView.getModelMap().put("model", new CreateFlightViewModel());
        return modelAndView;
    }
}
