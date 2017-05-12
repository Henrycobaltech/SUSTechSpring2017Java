package com.sustech.flightbooking.misc;

import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

public class SearchInfos {
    public static void addToModelAndView(ModelAndView modelAndView, String city, String flightNumber, LocalDate date) {
        modelAndView.getModelMap().put("searchCity", city);
        modelAndView.getModelMap().put("searchFlightNumber", flightNumber);
        modelAndView.getModelMap().put("searchDate", date);
    }
}
