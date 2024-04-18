package com.pink.itms.controller;

import com.pink.itms.dto.product.ProductRequestDTO;
import com.pink.itms.dto.product.ProductResponseDTO;
import com.pink.itms.exception.product.ProductNotFoundException;
import com.pink.itms.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * @param productRequestDTO
     * @return {@link ProductResponseDTO} - response from created product
     */
    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    /**
     * @param id
     * @return {@link ProductResponseDTO} - response from created product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> editProduct(@PathVariable long id, @RequestBody ProductRequestDTO requestDTO) {
        try {
            ProductResponseDTO responseDTO = productService.editProduct(id, requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    /**
     * @return list of all products
     */
    @GetMapping("")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());

    }

    /**
     * returns single product by id
     *
     * @param id id of product to be found
     * @return single product response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getSingle(@PathVariable long id) {
        try {
            return ResponseEntity.ok(productService.getSingle(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
