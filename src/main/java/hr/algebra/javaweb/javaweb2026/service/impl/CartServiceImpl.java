package hr.algebra.javaweb.javaweb2026.service.impl;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.dto.CartItemDTO;
import hr.algebra.javaweb.javaweb2026.dto.ProductDTO;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CartService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductService productService;

    @Override
    public ArrayList<CartItemDTO> getCartItems(HttpSession session) {
        Object cart = session.getAttribute(WebConstants.SessionAttributes.CART);

        if (cart == null) {
            ArrayList<CartItemDTO> emptyCart = new ArrayList<>();
            session.setAttribute(WebConstants.SessionAttributes.CART, emptyCart);
            return emptyCart;
        }

        return (ArrayList<CartItemDTO>) cart;
    }

    @Override
    public void addToCart(HttpSession session, String productName, Integer quantity) {
        int validQuantity = validateQuantity(quantity);
        ProductDTO product = findProductByName(productName);
        ArrayList<CartItemDTO> cartItems = getCartItems(session);

        CartItemDTO existingItem = findCartItem(cartItems, productName);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + validQuantity;
            validateStock(product, newQuantity);

            existingItem.setQuantity(newQuantity);
            existingItem.setSubtotal(calculateSubtotal(existingItem.getProductPrice(), newQuantity));
        } else {
            validateStock(product, validQuantity);

            CartItemDTO cartItem = new CartItemDTO(
                    product.getName(),
                    product.getPrice(),
                    validQuantity,
                    calculateSubtotal(product.getPrice(), validQuantity),
                    product.getImageUrl()
            );

            cartItems.add(cartItem);
        }

        session.setAttribute(WebConstants.SessionAttributes.CART, cartItems);
    }

    @Override
    public void updateQuantity(HttpSession session, String productName, Integer quantity) {
        int validQuantity = validateQuantity(quantity);
        ProductDTO product = findProductByName(productName);
        validateStock(product, validQuantity);

        ArrayList<CartItemDTO> cartItems = getCartItems(session);
        CartItemDTO existingItem = findCartItem(cartItems, productName);

        if (existingItem == null) {
            throw new ResourceNotFoundException("Product not found in cart: " + productName);
        }

        existingItem.setQuantity(validQuantity);
        existingItem.setSubtotal(calculateSubtotal(existingItem.getProductPrice(), validQuantity));

        session.setAttribute(WebConstants.SessionAttributes.CART, cartItems);
    }

    @Override
    public void removeFromCart(HttpSession session, String productName) {
        ArrayList<CartItemDTO> cartItems = getCartItems(session);

        cartItems.removeIf(item -> item.getProductName().equals(productName));

        session.setAttribute(WebConstants.SessionAttributes.CART, cartItems);
    }

    @Override
    public void clearCart(HttpSession session) {
        session.setAttribute(WebConstants.SessionAttributes.CART, new ArrayList<CartItemDTO>());
    }

    @Override
    public BigDecimal calculateTotal(HttpSession session) {
        return getCartItems(session)
                .stream()
                .map(CartItemDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ProductDTO findProductByName(String productName) {
        return productService.findByName(productName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productName));
    }

    private CartItemDTO findCartItem(ArrayList<CartItemDTO> cartItems, String productName) {
        return cartItems.stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    private int validateQuantity(Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }

        return quantity;
    }

    private void validateStock(ProductDTO product, int quantity) {
        if (quantity > product.getStockQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }
    }

    private BigDecimal calculateSubtotal(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}