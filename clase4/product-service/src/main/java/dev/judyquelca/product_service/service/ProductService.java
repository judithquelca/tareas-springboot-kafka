package dev.judyquelca.product_service.service;

import java.util.List;

import dev.judyquelca.product_service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.judyquelca.product_service.dto.ProductRequest;
import dev.judyquelca.product_service.dto.ProductResponse;
import dev.judyquelca.product_service.mapper.ProductMapper;
import dev.judyquelca.product_service.model.Category;
import dev.judyquelca.product_service.model.Product;
import dev.judyquelca.product_service.repository.CategoryRepository;
import dev.judyquelca.product_service.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
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
