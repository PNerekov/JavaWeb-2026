package hr.algebra.javaweb.javaweb2026.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String productName;

    @PositiveOrZero(message = "Product price cannot be negative")
    private BigDecimal productPrice;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @PositiveOrZero(message = "Product price cannot be negative")
    private BigDecimal subtotal;

    private String imageUrl;
}
