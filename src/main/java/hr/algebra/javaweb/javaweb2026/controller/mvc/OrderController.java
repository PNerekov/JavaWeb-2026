package hr.algebra.javaweb.javaweb2026.controller.mvc;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String showOrders(Authentication authentication, Model model){
        model.addAttribute(
                WebConstants.ModelAttributes.ORDERS,
                orderService.findOrdersForUser(authentication.getName())
        );

        return WebConstants.Views.ORDERS;
    }
}
