package com.sustech.flightbooking.controllers.manage;

import com.sustech.flightbooking.controllers.ControllerBase;
import com.sustech.flightbooking.persistence.OrderRepository;
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

    @Autowired
    public OrdersManagementController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("")
    public ModelAndView showAll() {
        List<OrderAdminViewModel> viewModels = orderRepository.findAll().stream()
                .map(OrderAdminViewModel::createFromDomainModel)
                .collect(Collectors.toList());
        ModelAndView modelAndView = page("admin/orders/list");
        modelAndView.getModelMap().put("orders", viewModels);
        return modelAndView;
    }
}
