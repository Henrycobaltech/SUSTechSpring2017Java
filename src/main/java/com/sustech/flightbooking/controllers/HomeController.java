package com.sustech.flightbooking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by Henry on 4/16/2017.
 */

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.put("now", new Date());
        return "index";
    }
}
