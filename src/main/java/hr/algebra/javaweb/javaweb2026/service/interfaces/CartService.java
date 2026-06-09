package hr.algebra.javaweb.javaweb2026.service.interfaces;

import hr.algebra.javaweb.javaweb2026.dto.CartItemDTO;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;


public interface CartService {
    ArrayList<CartItemDTO> getCartItems(HttpSession session);

    void addToCart(HttpSession session, String productName, Integer quantity);

    void updateQuantity(HttpSession session, String productName, Integer quantity);

    void removeFromCart(HttpSession session, String productName);

    void clearCart(HttpSession session);

    BigDecimal calculateTotal(HttpSession session);
}
