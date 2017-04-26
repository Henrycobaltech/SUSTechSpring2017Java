package com.sustech.flightbooking.infrastructure;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.services.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Henry on 4/26/2017.
 */

@Component
public class GlobalHandlerInterceptor extends HandlerInterceptorAdapter {

    private final IdentityService identityService;

    @Autowired
    public GlobalHandlerInterceptor(IdentityService identityService) {
        this.identityService = identityService;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView)
            throws Exception {
        if (modelAndView.hasView()) {
            addUserInfo(modelAndView);
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    private void addUserInfo(ModelAndView modelAndView) {
        FlightBookingUser user = identityService.getCurrentUser();
        if (user != null) {
            String displayName = user.getUserName();
            if (user instanceof Passenger)
                displayName = ((Passenger) user).getDisplayName();
            modelAndView.getModelMap().addAttribute("loggedIn", true);
            modelAndView.getModelMap().addAttribute("currentUserDisplayName", displayName);
        } else {
            modelAndView.getModelMap().addAttribute("loggedIn", false);
        }
    }
}
