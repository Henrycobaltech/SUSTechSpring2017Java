package com.sustech.flightbooking.controllers;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ControllerBase {

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

    public ModelAndView pageWithViewModel(String viewName, Object viewModel) {
        ModelAndView modelAndView = page(viewName);
        modelAndView.getModelMap().put("model", viewModel);
        return modelAndView;
    }

    public List<String> errorMessages(String... messages) {
        return Arrays.asList(messages);
    }
}
