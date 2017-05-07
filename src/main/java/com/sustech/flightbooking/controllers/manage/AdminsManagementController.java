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
        ModelAndView modelAndView = new ModelAndView("admin/admins/create");
        modelAndView.getModelMap().put("model", new CreateAdminViewModel());
        return modelAndView;
    }

    @GetMapping("{id}/edit")
    public ModelAndView editPage(@PathVariable UUID id) {
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

    @PostMapping("create")
    public View create(@ModelAttribute CreateAdminViewModel model) {
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
    public View update(@ModelAttribute EditAdminViewModel model, @PathVariable UUID id) {
        Administrator admin = adminsRepository.findById(id);
        if (admin == null) {
            return new ThymeleafView("/error/404.html");
        }
        admin.setUserName(model.getUserName());

        adminsRepository.save(admin);
        return redirect("/manage/admins");
    }
}
