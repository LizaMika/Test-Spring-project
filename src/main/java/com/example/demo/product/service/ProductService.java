package com.example.demo.product.service;

import com.example.demo.errors.exceptions.NotFoundException;
import com.example.demo.product.domain.Product;
import com.example.demo.product.dto.ProductRequest;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.dto.ProductUpdateDto;
import com.example.demo.product.mapper.ProductMapper;
import com.example.demo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findProductById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        if (productUpdateDto.getName() != null) {
            product.setName(productUpdateDto.getName());
        }
        if (productUpdateDto.isBlocked() != null) {
            product.setIsBlocked(productUpdateDto.isBlocked());
        }
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .toList();
    }


}
