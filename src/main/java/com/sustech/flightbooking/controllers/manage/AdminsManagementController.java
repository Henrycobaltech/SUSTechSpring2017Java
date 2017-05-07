package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.viewmodel.admins.CreateAdminViewModel;
import com.sustech.flightbooking.viewmodel.admins.EditAdminViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.ThymeleafView;

import java.util.UUID;

@Controller
@RequestMapping("/manage/admins")
public class AdminsManagementController extends ControllerBase {

    private final AdministratorsRepository adminsRepository;

    @Autowired
    public AdminsManagementController(AdministratorsRepository adminsRepository) {
        this.adminsRepository = adminsRepository;
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
                new CreateAdminViewModel());
    }

    @GetMapping("{id}/edit")
    public ModelAndView editPage(@PathVariable UUID id) {
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            return notFound();
        } else {
            EditAdminViewModel vm = new EditAdminViewModel();
            vm.setUserName(admin.getUserName());

            ModelAndView modelAndView = pageWithViewModel("admin/admins/edit", vm);
            modelAndView.getModel().put("adminId", admin.getId());
            return modelAndView;
        }
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute CreateAdminViewModel model) {
        Administrator admin = new Administrator(UUID.randomUUID());
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            // password does not match
        }
        admin.setUserName(model.getUserName());
        admin.setPassword(model.getPassword());

        adminsRepository.save(admin);
        return redirect("/manage/admins");
    }

    @PostMapping("{id}/update")
    public ModelAndView update(@ModelAttribute EditAdminViewModel model, @PathVariable UUID id) {
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            return notFound();
        }
        admin.setUserName(model.getUserName());

        adminsRepository.save(admin);
        return redirect("/manage/admins");
    }
}
