package dev.judyquelca.product_service.mapper;
import dev.judyquelca.product_service.dto.ProductRequest;
import dev.judyquelca.product_service.dto.ProductResponse;
import dev.judyquelca.product_service.model.Product;
import dev.judyquelca.product_service.model.Category;

public final class ProductMapper {

    private ProductMapper() {
        throw new AssertionError("Utility class, no debe instanciarse");
    }

    public static ProductResponse toResponse(Product product) {
        Category category = product.getCategory();
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null
        );
    }

    public static void updateEntity(ProductRequest request, Product entity, Category category) {
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        entity.setStock(request.stock());
        entity.setCategory(category);
    }
}
