package com.sustech.flightbooking.misc.responseHandling;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.function.Supplier;

public class ErrorMessageHandlerWithErrorMessages {
    private List<String> errorMessages;
    private ModelAndView modelAndView;

    ErrorMessageHandlerWithErrorMessages(ErrorMessageHandler errorMessageHandler, List<String> errorMessages) {
        this.modelAndView = errorMessageHandler.getModelAndView();
        this.errorMessages = errorMessages;
    }

    public ErrorMessageHandlerWithErrorAndSuccess onSuccess(Supplier<ModelAndView> successResult) {
        return new ErrorMessageHandlerWithErrorAndSuccess(this, successResult);
    }

    ModelAndView getModelAndView() {
        return modelAndView;
    }

    List<String> getErrorMessages() {
        return errorMessages;
    }
}
