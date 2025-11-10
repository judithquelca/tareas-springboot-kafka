package dev.judyquelca.product_service.service;

import dev.judyquelca.product_service.dto.ProductRequest;
import dev.judyquelca.product_service.dto.ProductResponse;
import dev.judyquelca.product_service.exception.ResourceNotFoundException;
import dev.judyquelca.product_service.kafka.event.ProductCreatedEvent;
import dev.judyquelca.product_service.kafka.producer.ProductEventProducer;
import dev.judyquelca.product_service.mapper.ProductMapper;
import dev.judyquelca.product_service.model.Category;
import dev.judyquelca.product_service.model.Product;
import dev.judyquelca.product_service.repository.CategoryRepository;
import dev.judyquelca.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductEventProducer productEventProducer;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductEventProducer productEventProducer) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productEventProducer = productEventProducer;
    }

    public List<ProductResponse> findAll(String name) {
        List<Product> products = (name == null || name.isBlank())
                ? productRepository.findAll()
                : productRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto " + id + " no encontrado"));
        return ProductMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría " + request.categoryId() + " no encontrada"));
        Product product = new Product();
        ProductMapper.updateEntity(request, product, category);
        Product savedProduct = productRepository.save(product);

        // 3. Publicar evento a Kafka
        ProductCreatedEvent event = new ProductCreatedEvent(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getCategory().getId()
        );
        productEventProducer.publishProductCreated(event);
        return ProductMapper.toResponse(savedProduct);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto " + id + " no encontrado"));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría " + request.categoryId() + " no encontrada"));
        ProductMapper.updateEntity(request, product, category);
        Product updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto " + id + " no encontrado"));
        productRepository.delete(product);
    }
}
