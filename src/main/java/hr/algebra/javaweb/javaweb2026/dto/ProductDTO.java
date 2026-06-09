package hr.algebra.javaweb.javaweb2026.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotEmpty(message = "Product name cannot be empty")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "Category name cannot be empty")
    private String categoryName;

    @NotEmpty(message = "Product description cannot be empty")
    @Size(min = 5, max = 1000, message = "Product description must be between 5 and 1000 characters.")
    private String description;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Stock Quantity cannot be null")
    @Min(value = 0, message ="Stock Quantity cannot be negative")
    private Integer stockQuantity;

    @Size(max = 2000, message = "Image URL cannot be longer than 2000 characters")
    private String imageUrl;

}
