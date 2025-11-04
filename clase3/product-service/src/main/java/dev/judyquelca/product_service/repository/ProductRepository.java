package dev.judyquelca.product_service.repository;

import java.util.List;
import dev.judyquelca.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "category")
    @Override
    List<Product> findAll();

    @EntityGraph(attributePaths = "category")
    List<Product> findByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Long categoryId);
}
