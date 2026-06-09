package hr.algebra.javaweb.javaweb2026.service.impl;

import hr.algebra.javaweb.javaweb2026.dto.CartItemDTO;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.form.HistorySearchForm;
import hr.algebra.javaweb.javaweb2026.model.Order;
import hr.algebra.javaweb.javaweb2026.model.OrderItem;
import hr.algebra.javaweb.javaweb2026.model.PaymentMethod;
import hr.algebra.javaweb.javaweb2026.model.User;
import hr.algebra.javaweb.javaweb2026.repository.OrderRepository;
import hr.algebra.javaweb.javaweb2026.repository.UserRepository;
import hr.algebra.javaweb.javaweb2026.service.interfaces.OrderService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.ProductService;
import hr.algebra.javaweb.javaweb2026.specification.OrderSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final String ORDER_STATUS_COMPLETED = "COMPLETED";

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public Order createOrder(String username,
                             List<CartItemDTO> cartItemsDTO,
                             BigDecimal totalPrice,
                             PaymentMethod paymentMethod) {
        if (cartItemsDTO == null || cartItemsDTO.isEmpty()){
           throw new IllegalArgumentException("Cart cannot be empty");
        }


        User user = userRepository.findByName(username);

        if(user == null){
            throw new ResourceNotFoundException("User not found: " + username);
        }

        for(CartItemDTO cartItemDTO : cartItemsDTO){
            productService.decreaseStock(cartItemDTO.getProductName(), cartItemDTO.getQuantity());
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);
        order.setPaymentMethod(paymentMethod);
        order.setStatus(ORDER_STATUS_COMPLETED);

        List<OrderItem> orderItems = cartItemsDTO.stream()
                .map(cartItem -> createOrderItem(cartItem, order))
                .toList();

        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersForUser(String username) {
        return orderRepository.findByUserNameOrderByOrderDateDesc(username);
    }

    private OrderItem createOrderItem(CartItemDTO cartItemDTO, Order order){
        OrderItem orderItem = new OrderItem();

        orderItem.setProductName(cartItemDTO.getProductName());
        orderItem.setProductPrice(cartItemDTO.getProductPrice());
        orderItem.setQuantity(cartItemDTO.getQuantity());
        orderItem.setSubtotal(cartItemDTO.getSubtotal());
        orderItem.setOrder(order);

        return orderItem;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrdersForAdmin(){
        return orderRepository.findAll(
                Sort.by(Sort.Direction.DESC, "orderDate")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersBySearchCriteria(HistorySearchForm historySearchForm){
        Specification<Order> specification =
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction();

        if(historySearchForm.getUsername() != null && !historySearchForm.getUsername().isBlank()){
            specification = specification.and(
                    OrderSpecification.usernameContains(historySearchForm.getUsername().trim())
            );
        }

        if(historySearchForm.getDateFrom() != null){
            specification = specification.and(
                    OrderSpecification.orderTimeFrom(historySearchForm.getDateFrom())
            );
        }

        if(historySearchForm.getDateTo() != null){
            specification = specification.and(
                    OrderSpecification.orderTimeTo(historySearchForm.getDateTo())
            );
        }

        return orderRepository.findAll(
                specification,
                Sort.by(Sort.Direction.DESC, "orderDate")
        );
    }
}
