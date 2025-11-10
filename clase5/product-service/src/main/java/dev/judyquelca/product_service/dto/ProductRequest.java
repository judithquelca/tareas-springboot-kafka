package dev.judyquelca.product_service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;


public record ProductRequest(
        @NotBlank
        @Size(max = 120)
        String name,

        @Size(max = 255)
        String description,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price,

        @NotNull
        @PositiveOrZero
        Integer stock,

        @NotNull
        Long categoryId
) {
}
