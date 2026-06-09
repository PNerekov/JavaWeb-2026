package hr.algebra.javaweb.javaweb2026.controller.mvc;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return WebConstants.Views.HOME;
    }
}
