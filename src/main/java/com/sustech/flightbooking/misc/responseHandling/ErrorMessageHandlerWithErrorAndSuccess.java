package com.sustech.flightbooking.misc.responseHandling;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.function.Supplier;

public class ErrorMessageHandlerWithErrorAndSuccess {

    private final ModelAndView modelAndView;
    private final Supplier<ModelAndView> successResult;
    private final List<String> errorMessages;

    ErrorMessageHandlerWithErrorAndSuccess(
            ErrorMessageHandlerWithErrorMessages errorMessageHandlerWithErrorMessages,
            Supplier<ModelAndView> successResult) {
        this.modelAndView = errorMessageHandlerWithErrorMessages.getModelAndView();
        this.errorMessages = errorMessageHandlerWithErrorMessages.getErrorMessages();
        this.successResult = successResult;
    }


    public ModelAndView result() {
        if (this.errorMessages.size() > 0) {
            modelAndView.getModelMap().put("errorMessages", errorMessages);
            return modelAndView;
        }
        return successResult.get();
    }
}
