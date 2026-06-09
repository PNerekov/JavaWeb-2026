package hr.algebra.javaweb.javaweb2026.specification;

import hr.algebra.javaweb.javaweb2026.model.LoginHistory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class LoginHistorySpecification {

    private LoginHistorySpecification(){
    }

    public static Specification<LoginHistory> usernameContains(String username){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("user").get("name")),
                        "%" + username.toLowerCase() + "%"
                );
    }

    public static Specification<LoginHistory> loginTimeFrom(LocalDate dateFrom){
        LocalDateTime from = dateFrom.atStartOfDay();

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("loginTime"), from);
    }

    public static Specification<LoginHistory> loginTimeTo(LocalDate dateTo){
        LocalDateTime to = dateTo.plusDays(1).atStartOfDay();

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("loginTime"), to);
    }
}
