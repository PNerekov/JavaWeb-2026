package hr.algebra.javaweb.javaweb2026.controller.mvc;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.exeptions.RegistrationException;
import hr.algebra.javaweb.javaweb2026.model.UserRegistrationDTO;
import hr.algebra.javaweb.javaweb2026.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute(
                WebConstants.ModelAttributes.USER_REGISTRATION_DTO,
                new UserRegistrationDTO()
        );

        return WebConstants.Views.REGISTER;
    }

    @PostMapping
    public String register(
            @Valid @ModelAttribute(WebConstants.ModelAttributes.USER_REGISTRATION_DTO)
            UserRegistrationDTO userRegistrationDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        if (bindingResult.hasErrors()){
            return WebConstants.Views.REGISTER;
        }

        try{
            userService.register(userRegistrationDTO);

            redirectAttributes.addFlashAttribute(
                    WebConstants.ModelAttributes.SUCCESS_MESSAGE,
                    "Registration successful. You can now log in"
            );

            return WebConstants.Redirects.LOGIN;
        }catch (RegistrationException e){
            model.addAttribute(WebConstants.ModelAttributes.ERROR_MESSAGE, e.getMessage());

            return WebConstants.Views.REGISTER;
        }

    }
}
