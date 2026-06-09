package hr.algebra.javaweb.javaweb2026.specification;

import hr.algebra.javaweb.javaweb2026.model.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class OrderSpecification {

    private OrderSpecification(){
    }

    public static Specification<Order> usernameContains(String username){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("user").get("name")),
                        "%" + username.toLowerCase() + "%"
                );
    }

    public static Specification<Order> orderTimeFrom(LocalDate dateFrom){
        LocalDateTime from = dateFrom.atStartOfDay();

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), from);
    }

    public static Specification<Order> orderTimeTo(LocalDate dateTo){
        LocalDateTime to = dateTo.plusDays(1).atStartOfDay();

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("orderDate"), to);
    }
}
