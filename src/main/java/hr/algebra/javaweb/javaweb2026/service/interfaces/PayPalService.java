package hr.algebra.javaweb.javaweb2026.service.interfaces;

import java.math.BigDecimal;

public interface PayPalService {

    String createOrder(BigDecimal totalPrice);

    void captureOrder(String paypalOrderId);
}
