package com.sustech.flightbooking.controllers;

import com.sustech.flightbooking.persistence.AdministratorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by Henry on 4/16/2017.
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.put("now", new Date());
        return "index";
    }
}
