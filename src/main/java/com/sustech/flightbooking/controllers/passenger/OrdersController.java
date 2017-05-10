package com.sustech.flightbooking.controllers.passenger;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.domainmodel.OrderStatus;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.OrderRepository;
import com.sustech.flightbooking.services.IdentityService;
import com.sustech.flightbooking.viewmodel.passenger.order.OrderPassengerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/passenger/orders")
public class OrdersController extends ControllerBase {

    private final OrderRepository orderRepository;
    private final IdentityService identityService;

    @Autowired
    public OrdersController(OrderRepository orderRepository, IdentityService identityService) {
        this.orderRepository = orderRepository;
        this.identityService = identityService;
    }

    @GetMapping("")
    public ModelAndView showAll() {
        Passenger passenger = (Passenger) identityService.getCurrentUser();
        List<Order> orders = orderRepository.findByPassenger(passenger);
        List<OrderPassengerViewModel> viewModels = orders.stream()
                .map(OrderPassengerViewModel::createFromViewModel)
                .collect(Collectors.toList());
        ModelAndView modelAndView = page("passenger/orders/list");
        modelAndView.getModelMap().put("orders", viewModels);
        return modelAndView;
    }

    @GetMapping("{id}/pay")
    public ModelAndView paymentPage(@PathVariable UUID id) {
        Order order = orderRepository.findById(id);
        if (order == null ||
                order.getStatus() != OrderStatus.UNPAID
                || !order.getPassenger().equals(identityService.getCurrentUser())) {
            return notFound();
        }
        ModelAndView modelAndView = page("passenger/orders/pay");
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("orderId", order.getId());
        modelMap.put("flight", order.getFlight());
        modelMap.put("passenger", order.getPassenger());
        modelMap.put("seat", order.getSeat());
        return modelAndView;
    }

    @PostMapping("{id}/pay")
    public ModelAndView pay(@PathVariable UUID id) {
        Order order = orderRepository.findById(id);
        if (order == null ||
                order.getStatus() != OrderStatus.UNPAID
                || !order.getPassenger().equals(identityService.getCurrentUser())) {
            return notFound();
        }
        order.pay();
        orderRepository.save(order);
        return redirect("/passenger/orders");
    }

    @GetMapping("{id}/cancel")
    public ModelAndView cancelPage(@PathVariable UUID id) {
        Order order = orderRepository.findById(id);
        if (order == null ||
                order.getStatus() != OrderStatus.PAID
                || !order.getPassenger().equals(identityService.getCurrentUser())) {
            return notFound();
        }
        ModelAndView modelAndView = page("passenger/orders/cancel");
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.put("orderId", order.getId());
        modelMap.put("flight", order.getFlight());
        modelMap.put("passenger", order.getPassenger());
        modelMap.put("seat", order.getSeat());
        return modelAndView;
    }

    @PostMapping("{id}/cancel")
    public ModelAndView cancel(@PathVariable UUID id) {
        Order order = orderRepository.findById(id);
        if (order == null ||
                order.getStatus() != OrderStatus.PAID
                || !order.getPassenger().equals(identityService.getCurrentUser())) {
            return notFound();
        }
        order.cancel();
        orderRepository.save(order);
        return redirect("/passenger/orders");
    }

}