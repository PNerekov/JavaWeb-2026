package hr.algebra.javaweb.javaweb2026.controller.mvc;


import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String showCart(HttpSession session, Model model){
        model.addAttribute(WebConstants.ModelAttributes.CART_ITEMS, cartService.getCartItems(session));
        model.addAttribute(WebConstants.ModelAttributes.CART_TOTAL, cartService.calculateTotal(session));

        return WebConstants.Views.CART;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam String productName,
                            @RequestParam Integer quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            cartService.addToCart(session, productName, quantity);
            redirectAttributes.addFlashAttribute(WebConstants.ModelAttributes.SUCCESS_MESSAGE, "Product added to cart.");
            return WebConstants.Redirects.CART;
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(WebConstants.ModelAttributes.ERROR_MESSAGE, exception.getMessage());
            return WebConstants.Redirects.PRODUCTS;
        }
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam String productName,
                                 @RequestParam Integer quantity,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        try {
            cartService.updateQuantity(session, productName, quantity);
            redirectAttributes.addFlashAttribute(WebConstants.ModelAttributes.SUCCESS_MESSAGE, "Cart updated.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(WebConstants.ModelAttributes.ERROR_MESSAGE, e.getMessage());
        }

        return WebConstants.Redirects.CART;
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam String productName,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        cartService.removeFromCart(session, productName);
        redirectAttributes.addFlashAttribute(WebConstants.ModelAttributes.SUCCESS_MESSAGE, "Product removed from cart.");

        return WebConstants.Redirects.CART;
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session,
                            RedirectAttributes redirectAttributes) {
        cartService.clearCart(session);
        redirectAttributes.addFlashAttribute(WebConstants.ModelAttributes.SUCCESS_MESSAGE, "Cart cleared.");

        return WebConstants.Redirects.CART;
    }
}
