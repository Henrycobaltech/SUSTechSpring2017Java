package com.sustech.flightbooking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ControllerBase {

    public ModelAndView page(String viewName) {
        return new ModelAndView(viewName);
    }

    public ModelAndView redirect(String url) {
        RedirectView redirectView = new RedirectView(url);
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
    }

    public ModelAndView notFound() {
        return notFound("");
    }

    public ModelAndView notFound(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.getModelMap().put("errorMessage", errorMessage);
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    public ModelAndView badRequest() {
        return badRequest("");
    }

    public ModelAndView badRequest(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView("/error/400");
        modelAndView.getModelMap().put("errorMessage", errorMessage);
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}
