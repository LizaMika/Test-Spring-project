package com.example.demo.product.repository;

import com.example.demo.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductsById(Long id);
    Optional<Product> findProductById(Long id);
}
