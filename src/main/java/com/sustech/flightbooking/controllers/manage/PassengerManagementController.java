package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.viewmodel.PassengerEditModelViewModel;
import com.sustech.flightbooking.viewmodel.ViewModelValidator;
import com.sustech.flightbooking.viewmodel.manage.passengers.EditPassengerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/manage/passengers")
public class PassengerManagementController extends ControllerBase {

    private final PassengerRepository passengerRepository;
    private final AdministratorsRepository adminRepository;

    @Autowired
    public PassengerManagementController(PassengerRepository passengerRepository,
                                         AdministratorsRepository adminRepository) {
        this.passengerRepository = passengerRepository;
        this.adminRepository = adminRepository;
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
    public ModelAndView update(@ModelAttribute PassengerEditModelViewModel model, @PathVariable UUID id) {
        Passenger passenger = passengerRepository.findById(id);
        List<String> errorMessages = ViewModelValidator.validate(model);
        if (passenger == null) {
            return notFound();
        }
        if (adminRepository.findByUserName(model.getUserName()) != null) {
            errorMessages.add("User name already exists.");
        }
        if (!passengerRepository.findByUserName(model.getUserName()).equals(passenger)) {
            errorMessages.add("User name already exists.");
        }
        if (!passengerRepository.findByIdCard(model.getIdentityNumber()).equals(passenger)) {
            errorMessages.add("ID card is already registered.");
        }
        if (errorMessages.size() > 0) {
            ModelAndView modelAndView = pageWithViewModel("register", model);
            modelAndView.getModelMap().put("errorMessages", errorMessages);
            return modelAndView;
        }
        passenger.setUserName(model.getUserName());
        passenger.setDisplayName(model.getDisplayName());
        passenger.setIdentityCardNumber(model.getIdentityNumber());
        passenger.setPassword(model.getPassword());

        passengerRepository.save(passenger);
        return redirect("/manage/passengers");
    }
}
