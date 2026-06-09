package hr.algebra.javaweb.javaweb2026.service.interfaces;

import hr.algebra.javaweb.javaweb2026.dto.CartItemDTO;
import hr.algebra.javaweb.javaweb2026.form.HistorySearchForm;
import hr.algebra.javaweb.javaweb2026.model.Order;
import hr.algebra.javaweb.javaweb2026.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Order createOrder(String username,
                      List<CartItemDTO> cartItemsDTO,
                      BigDecimal totalPrice,
                      PaymentMethod paymentMethod);

    List<Order> findOrdersForUser(String username);

    List<Order> findAllOrdersForAdmin();

    List<Order> findOrdersBySearchCriteria(HistorySearchForm historySearchForm);
}
