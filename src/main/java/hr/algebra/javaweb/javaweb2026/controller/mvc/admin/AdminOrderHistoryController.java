package hr.algebra.javaweb.javaweb2026.controller.mvc.admin;


import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.form.HistorySearchForm;
import hr.algebra.javaweb.javaweb2026.service.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders")
@AllArgsConstructor
public class AdminOrderHistoryController {

    private final OrderService orderService;

    @GetMapping
    public String showOrders(Model model){
        model.addAttribute(
                WebConstants.ModelAttributes.HISTORY_SEARCH_FORM,
                new HistorySearchForm()
        );

        model.addAttribute(
                WebConstants.ModelAttributes.ORDERS,
                orderService.findAllOrdersForAdmin()
        );

        return WebConstants.Views.ADMIN_ORDERS;
    }

    @PostMapping
    public String searchOrders(
            @ModelAttribute(WebConstants.ModelAttributes.HISTORY_SEARCH_FORM)
            HistorySearchForm historySearchForm,
            Model model
    ){
        model.addAttribute(
                WebConstants.ModelAttributes.ORDERS,
                orderService.findOrdersBySearchCriteria(historySearchForm)
        );

        return WebConstants.Views.ADMIN_ORDERS;
    }
}

