package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.viewmodel.ViewModelValidator;
import com.sustech.flightbooking.viewmodel.manage.admins.AdminEditViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/manage/admins")
public class AdminsManagementController extends ControllerBase {

    private final AdministratorsRepository adminsRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public AdminsManagementController(AdministratorsRepository adminsRepository, PassengerRepository passengerRepository) {
        this.adminsRepository = adminsRepository;
        this.passengerRepository = passengerRepository;
    }

    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView("admin/admins/list");
        modelAndView.getModel().put("admins", adminsRepository.findAll());
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView createPage() {
        return pageWithViewModel("admin/admins/create",
                new AdminEditViewModel());
    }

    @GetMapping("{id}/edit")
    public ModelAndView editPage(@PathVariable UUID id) {
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            return notFound();
        } else {
            AdminEditViewModel vm = new AdminEditViewModel();
            vm.setUserName(admin.getUserName());

            ModelAndView modelAndView = pageWithViewModel("admin/admins/edit", vm);
            modelAndView.getModel().put("adminId", admin.getId());
            return modelAndView;
        }
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute AdminEditViewModel model) {
        List<String> errorMessages = ViewModelValidator.validate(model);
        if (adminsRepository.findByUserName(model.getUserName()) != null) {
            errorMessages.add("User name already exists.");
        }
        if (passengerRepository.findByUserName(model.getUserName()) != null) {
            errorMessages.add("User name already exists.");
        }
        if (errorMessages.size() > 0) {
            return pageWithErrorMessages("admin/admins/create", model, errorMessages);
        }
        Administrator admin = new Administrator(UUID.randomUUID());
        admin.setUserName(model.getUserName());
        admin.setPassword(model.getPassword());
        adminsRepository.save(admin);
        return redirect("/manage/admins");
    }

    @PostMapping("{id}/update")
    public ModelAndView update(@ModelAttribute AdminEditViewModel model, @PathVariable UUID id) {
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            return notFound();
        }
        List<String> errorMessages = ViewModelValidator.validate(model);
        if (!adminsRepository.findByUserName(model.getUserName()).equals(admin)) {
            errorMessages.add("User name already exists.");
        }
        if (errorMessages.size() > 0) {
            return pageWithErrorMessages("admin/admins/edit", model, errorMessages);
        }
        admin.setUserName(model.getUserName());
        admin.setPassword(model.getPassword());
        adminsRepository.save(admin);
        return redirect("/manage/admins");
    }
}
