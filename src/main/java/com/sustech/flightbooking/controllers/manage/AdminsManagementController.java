package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.misc.responseHandling.ErrorMessageHandler;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.services.UserService;
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
    private final UserService userService;

    @Autowired
    public AdminsManagementController(AdministratorsRepository adminsRepository,
                                      UserService userService) {
        this.adminsRepository = adminsRepository;
        this.userService = userService;
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
        return createOrUpdate(model, UUID.randomUUID(), "admin/admins/create");
    }

    @PostMapping("{id}/update")
    public ModelAndView update(@ModelAttribute AdminEditViewModel model, @PathVariable UUID id) {
        if (adminsRepository.findById(id) == null) {
            return notFound();
        }
        return createOrUpdate(model, id, "admin/admins/edit");
    }

    private ModelAndView createOrUpdate(@ModelAttribute AdminEditViewModel model, UUID id, String viewName) {
        List<String> errorMessages = ViewModelValidator.validate(model);
        Administrator admin = adminsRepository.findById(id);
        admin = admin != null ? admin : new Administrator(id);
        if (userService.isUserNameRegisteredFor(admin, model.getUserName())) {
            errorMessages.add("User name already exists.");
        }
        Administrator finalAdmin = admin;
        return ErrorMessageHandler.fromViewModel(model, viewName)
                .addErrorMessages(errorMessages)
                .onSuccess(() -> {
                    finalAdmin.setUserName(model.getUserName());
                    finalAdmin.setPassword(model.getPassword());
                    adminsRepository.save(finalAdmin);
                    return redirect("/manage/admins");
                })
                .result();
    }
}
