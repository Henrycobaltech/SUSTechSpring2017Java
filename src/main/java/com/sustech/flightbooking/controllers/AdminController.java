package com.sustech.flightbooking.controllers;

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
}
