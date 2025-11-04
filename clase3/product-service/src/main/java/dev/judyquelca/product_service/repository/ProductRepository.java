package dev.judyquelca.product_service.repository;

import java.util.List;
import dev.judyquelca.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);
}
