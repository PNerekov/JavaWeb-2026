package hr.algebra.javaweb.javaweb2026.dto;

import hr.algebra.javaweb.javaweb2026.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDTO {

    @NotNull(message = "Payment method must be selected.")
    private PaymentMethod paymentMethod;
}
