package hr.algebra.javaweb.javaweb2026.controller.mvc.admin;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.form.HistorySearchForm;
import hr.algebra.javaweb.javaweb2026.service.interfaces.LoginHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/login-history")
@AllArgsConstructor
public class AdminLoginHistoryController {

    private final LoginHistoryService loginHistoryService;

    @GetMapping
    public String showLoginHistory(Model model){
        model.addAttribute(
                WebConstants.ModelAttributes.HISTORY_SEARCH_FORM,
                new HistorySearchForm()
        );

        model.addAttribute(
                WebConstants.ModelAttributes.LOGIN_HISTORY_RECORDS,
                loginHistoryService.findAll()
        );

        return WebConstants.Views.ADMIN_LOGIN_HISTORY;
    }

    @PostMapping
    public String searchLoginHistory(
            @ModelAttribute(WebConstants.ModelAttributes.HISTORY_SEARCH_FORM)
            HistorySearchForm historySearchForm,
            Model model
    ){
        model.addAttribute(
                WebConstants.ModelAttributes.LOGIN_HISTORY_RECORDS,
                loginHistoryService.findBySearchCriteria(historySearchForm)
        );

        return WebConstants.Views.ADMIN_LOGIN_HISTORY;
    }

}
