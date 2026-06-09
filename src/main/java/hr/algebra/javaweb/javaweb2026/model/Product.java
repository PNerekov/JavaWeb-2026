package hr.algebra.javaweb.javaweb2026.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    private Integer stockQuantity;
    @Column(length = 2000)
    private String imageUrl;
    private BigDecimal price;
}
