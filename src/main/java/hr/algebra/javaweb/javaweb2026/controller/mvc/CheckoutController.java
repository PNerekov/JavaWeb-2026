package hr.algebra.javaweb.javaweb2026.controller.mvc;

import hr.algebra.javaweb.javaweb2026.configuration.PayPalProperties;
import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.dto.CheckoutDTO;
import hr.algebra.javaweb.javaweb2026.model.Order;
import hr.algebra.javaweb.javaweb2026.model.PaymentMethod;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CartService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final PayPalProperties payPalProperties;

    @GetMapping
    public String showCheckout(HttpSession session, Model model){
        if (cartService.getCartItems(session).isEmpty()){
            return WebConstants.Redirects.CART;
        }

        prepareCheckoutModel(session, model, new CheckoutDTO());

        return WebConstants.Views.CHECKOUT;
    }

    @PostMapping
    public String checkout(@Valid @ModelAttribute(WebConstants.ModelAttributes.CHECKOUT_DTO) CheckoutDTO checkoutDTO,
                           BindingResult bindingResult,
                           HttpSession session,
                           Authentication authentication,
                           Model model){
        if(cartService.getCartItems(session).isEmpty()){
            return WebConstants.Redirects.CART;
        }

        if(bindingResult.hasErrors()){
            prepareCheckoutModel(session, model, checkoutDTO);
            return WebConstants.Views.CHECKOUT;
        }

        if (checkoutDTO.getPaymentMethod() == PaymentMethod.PAYPAL) {
            model.addAttribute(
                    WebConstants.ModelAttributes.ERROR_MESSAGE,
                    "Please use the PayPal button to complete PayPal payment."
            );

            prepareCheckoutModel(session, model, checkoutDTO);

            return WebConstants.Views.CHECKOUT;
        }

        try {
            Order order = orderService.createOrder(
                    authentication.getName(),
                    cartService.getCartItems(session),
                    cartService.calculateTotal(session),
                    checkoutDTO.getPaymentMethod()
            );

            cartService.clearCart(session);

            model.addAttribute(WebConstants.ModelAttributes.ORDER, order);

            return WebConstants.Views.CHECKOUT_SUCCESS;
        } catch (IllegalArgumentException exception) {
            model.addAttribute(WebConstants.ModelAttributes.ERROR_MESSAGE, exception.getMessage());
            prepareCheckoutModel(session, model, checkoutDTO);
            return WebConstants.Views.CHECKOUT;
        }
    }

    private void prepareCheckoutModel(HttpSession session, Model model, CheckoutDTO checkoutDTO){
        model.addAttribute(WebConstants.ModelAttributes.CHECKOUT_DTO, checkoutDTO);
        model.addAttribute(WebConstants.ModelAttributes.CART_ITEMS, cartService.getCartItems(session));
        model.addAttribute(WebConstants.ModelAttributes.CART_TOTAL, cartService.calculateTotal(session));
        model.addAttribute(WebConstants.ModelAttributes.PAYMENT_METHODS, PaymentMethod.values());
        model.addAttribute(WebConstants.ModelAttributes.PAYPAL_CLIENT_ID, payPalProperties.clientId());
    }
}
