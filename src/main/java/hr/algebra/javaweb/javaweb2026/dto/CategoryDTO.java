package hr.algebra.javaweb.javaweb2026.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotEmpty(message = "Category name cannot be empty.")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;
}
