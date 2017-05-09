package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.persistence.OrderRepository;
import com.sustech.flightbooking.services.OrderService;
import com.sustech.flightbooking.viewmodel.manage.OrderAdminViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/orders")
public class OrdersManagementController extends ControllerBase {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    public OrdersManagementController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("")
    public ModelAndView showAll() {
        List<OrderAdminViewModel> viewModels = orderRepository.findAll().stream()
                .map(order -> {
                    OrderAdminViewModel orderVm = new OrderAdminViewModel();
                    orderVm.setCreationTime(order.getCreatedTime());
                    orderVm.setSeat(order.getSeat());
                    orderVm.setPassengerName(order.getPassenger().getDisplayName());
                    orderVm.setFlightId(order.getFlight().getId());
                    orderVm.setStatus(orderService.getStatus(order));
                    orderVm.setFlightNumber(order.getFlight().getFlightNumber());
                    return orderVm;
                })
                .collect(Collectors.toList());
        ModelAndView modelAndView = page("admin/orders/list");
        modelAndView.getModelMap().put("orders", viewModels);
        return modelAndView;
    }
}
