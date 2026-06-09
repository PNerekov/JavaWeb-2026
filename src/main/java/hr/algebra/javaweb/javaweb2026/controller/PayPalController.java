package hr.algebra.javaweb.javaweb2026.controller;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.model.Order;
import hr.algebra.javaweb.javaweb2026.model.PaymentMethod;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CartService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.OrderService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.PayPalService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/paypal")
@AllArgsConstructor
public class PayPalController {

    private final PayPalService payPalService;
    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<Map<String, String>> createPayPalOrder(HttpSession session){
        if(cartService.getCartItems(session).isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is empty"));
        }

        String paypalOrderId = payPalService.createOrder(cartService.calculateTotal(session));

        return ResponseEntity.ok(Map.of("id", paypalOrderId));
    }


    @PostMapping("/capture-order")
    public ResponseEntity<Map<String, Object>> capturePayPalOrder(@RequestParam String paypalOrderId,
                                                                  HttpSession session,
                                                                  Authentication authentication){
        if(cartService.getCartItems(session).isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is Empty"));
        }

        payPalService.captureOrder(paypalOrderId);

        Order order = orderService.createOrder(
                authentication.getName(),
                cartService.getCartItems(session),
                cartService.calculateTotal(session),
                PaymentMethod.PAYPAL
        );

        cartService.clearCart(session);

        return ResponseEntity.ok(Map.of(
                WebConstants.Values.SUCCESS, true,
                "orderId", order.getId()
        ));
    }
}
