package com.sustech.flightbooking.misc.responseHandling;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class ErrorMessageHandler {

    private ModelAndView modelAndView;

    private ErrorMessageHandler(ModelAndView modelAndView) {

        this.modelAndView = modelAndView;
    }

    private ErrorMessageHandler() {
    }

    private static ModelAndView pageWithViewModel(String viewName, Object viewModel) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.getModelMap().put("model", viewModel);
        return modelAndView;
    }

    public static ErrorMessageHandler fromViewModel(Object viewModel, String submitView) {

        return new ErrorMessageHandler(pageWithViewModel(submitView, viewModel));
    }

    public ErrorMessageHandler putModelMap(String key, Object value) {
        this.modelAndView.getModelMap().put(key, value);
        return this;
    }

    public ErrorMessageHandlerWithErrorMessages addErrorMessages(List<String> errorMessages) {
        return new ErrorMessageHandlerWithErrorMessages(this, errorMessages);
    }

    ModelAndView getModelAndView() {
        return modelAndView;
    }
}
