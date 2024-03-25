package com.pink.itms.service;

import com.pink.itms.dto.product.ProductRequestDTO;
import com.pink.itms.dto.product.ProductResponseDTO;
import com.pink.itms.mapper.ProductMapper;
import com.pink.itms.model.Product;
import com.pink.itms.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * creates and saves product entity
     * @param productRequestDTO product to create
     * @return {@link ProductResponseDTO} - response from created product
     */
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }


}
