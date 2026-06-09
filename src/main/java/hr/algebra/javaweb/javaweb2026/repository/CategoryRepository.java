package hr.algebra.javaweb.javaweb2026.repository;

import hr.algebra.javaweb.javaweb2026.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameIgnoreCase(String name);
}
