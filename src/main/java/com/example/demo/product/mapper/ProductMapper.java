package com.example.demo.product.mapper;

import com.example.demo.product.domain.Product;
import com.example.demo.product.dto.ProductRequest;
import com.example.demo.product.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(target = "id", ignore = true)
    public abstract Product toProduct(ProductRequest productRequest);

    public abstract ProductResponse toProductResponse(Product product);
}
