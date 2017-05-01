package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.viewmodel.CreateAdminViewModel;
import com.sustech.flightbooking.viewmodel.CreatePassengerViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Henry on 4/23/2017.
 */

@Controller
@RequestMapping("/manage")
public class AdminController {

    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }

    @GetMapping("passengers")
    public ModelAndView passengers() {
        ModelAndView modelAndView = new ModelAndView("admin/passengers/list");
        //add statistic info here
        return modelAndView;
    }

    @GetMapping("passengers/create")
    public ModelAndView createPassenger() {
        ModelAndView modelAndView = new ModelAndView("admin/passengers/create");
        modelAndView.getModelMap().addAttribute("model", new CreatePassengerViewModel());
        return modelAndView;
    }

    @GetMapping("admins/create")
    public ModelAndView createAdmin() {
        ModelAndView modelAndView = new ModelAndView("admin/admins/create");
        modelAndView.getModelMap().addAttribute("model", new CreateAdminViewModel());
        return modelAndView;
    }
}
