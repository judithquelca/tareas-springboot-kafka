package dev.judyquelca.product_service.dto;
import java.math.BigDecimal;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "{product.name.notblank}")
        @Size(max = 120)
        String name,

        @Size(max = 255)
        String description,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false, message = "{product.price.min}")
        BigDecimal price,

        @NotNull
        @PositiveOrZero(message = "{product.stock.min}")
        Integer stock
) {}
