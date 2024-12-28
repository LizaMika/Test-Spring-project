package com.example.demo.product.controller;

import com.example.demo.errors.exceptions.NotFoundException;
import com.example.demo.product.domain.Product;
import com.example.demo.product.dto.ProductRequest;
import com.example.demo.product.dto.ProductResponse;
import com.example.demo.product.dto.ProductUpdateDto;
import com.example.demo.product.mapper.ProductMapper;
import com.example.demo.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: 1. Почему продуктами может управлять кто угодно?

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse product = productService.createProduct(productRequest);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        ProductResponse productResponse = productMapper.toProductResponse(product);
        return ResponseEntity.ok(productResponse);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDto productUpdateDto) {
        ProductResponse product = productService.updateProduct(id, productUpdateDto);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}