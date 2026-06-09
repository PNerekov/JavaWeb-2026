package hr.algebra.javaweb.javaweb2026.repository;

import hr.algebra.javaweb.javaweb2026.model.Order;
import hr.algebra.javaweb.javaweb2026.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByUser(User user);

    List<Order> findByUserOrderByOrderDateDesc(User user);

    @EntityGraph(attributePaths = "orderItems")
    List<Order> findByUserNameOrderByOrderDateDesc(String username);

}
