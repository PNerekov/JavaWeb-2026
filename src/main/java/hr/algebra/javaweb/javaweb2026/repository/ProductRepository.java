package hr.algebra.javaweb.javaweb2026.repository;

import hr.algebra.javaweb.javaweb2026.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryNameIgnoreCase(String categoryName);
    List<Product> findByName(String name);
}
