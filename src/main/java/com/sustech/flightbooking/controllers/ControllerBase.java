package com.sustech.flightbooking.controllers;

import org.springframework.web.servlet.view.RedirectView;

public class ControllerBase {
    public RedirectView redirect(String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }
}
