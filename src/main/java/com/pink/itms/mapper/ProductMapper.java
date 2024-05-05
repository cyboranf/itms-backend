package com.pink.itms.mapper;

import com.pink.itms.dto.product.ProductRequestDTO;
import com.pink.itms.dto.product.ProductResponseDTO;
import com.pink.itms.model.Product;
import com.pink.itms.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    ProductRepository productRepository;

    public ProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product toEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setCode(productRequestDTO.getCode());
        product.setHeight(productRequestDTO.getHeight());
        product.setLength(productRequestDTO.getLength());
        product.setWidth(productRequestDTO.getWidth());
        product.setWeight(productRequestDTO.getWeight());
        product.setIsActive(true);

        return product;
    }

    public ProductResponseDTO toDto(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setCode(product.getCode());
        productResponseDTO.setHeight(product.getHeight());
        productResponseDTO.setLength(product.getLength());
        productResponseDTO.setWidth(product.getWidth());
        productResponseDTO.setWeight(product.getWeight());
        productResponseDTO.setIsActive(product.getIsActive());

        return productResponseDTO;
    }
}
