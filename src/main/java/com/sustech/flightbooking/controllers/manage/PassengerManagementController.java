package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.viewmodel.passengers.CreatePassengerViewModel;
import com.sustech.flightbooking.viewmodel.passengers.EditPassengerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.ThymeleafView;

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
        ModelAndView modelAndView = new ModelAndView("admin/passengers/list");
        modelAndView.getModel().put("passengers", passengerRepository.findAll());
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView createPage() {
        ModelAndView modelAndView = new ModelAndView("admin/passengers/create");
        modelAndView.getModelMap().put("model", new CreatePassengerViewModel());
        return modelAndView;
    }

    @GetMapping("{id}/edit")
    public ModelAndView editPage(@PathVariable UUID id) {
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

    @PostMapping("create")
    public View create(@ModelAttribute CreatePassengerViewModel model) {
        Passenger passenger = new Passenger(UUID.randomUUID());

        passenger.setUserName(model.getUserName());
        passenger.setDisplayName(model.getDisplayName());
        passenger.setIdentityCardNumber(model.getIdentityNumber());

        passengerRepository.save(passenger);
        return redirect("/manage/passengers");
    }

    @PostMapping("{id}/update")
    public View update(@ModelAttribute CreatePassengerViewModel model, @PathVariable UUID id) {
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
}
