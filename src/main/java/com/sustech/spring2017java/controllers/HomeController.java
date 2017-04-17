package com.sustech.spring2017java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Henry on 4/16/2017.
 */

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
