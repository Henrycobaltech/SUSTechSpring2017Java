package com.sustech.flightbooking.controllers.manage;


import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.viewmodel.CreateFlightViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("manage/flights")
public class FlightsManagementController extends ControllerBase {

    @GetMapping("create")
    public ModelAndView createFlight() {
        ModelAndView modelAndView = new ModelAndView("admin/flights/create");
        modelAndView.getModelMap().put("model", new CreateFlightViewModel());
        return modelAndView;
    }
}
