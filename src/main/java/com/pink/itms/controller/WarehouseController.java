package com.pink.itms.controller;

import com.pink.itms.dto.warehouse.WarehouseRequestDTO;
import com.pink.itms.dto.warehouse.WarehouseResponseDTO;
import com.pink.itms.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * @param warehouseRequestDTO
     * @return {@link WarehouseResponseDTO} - response from created warehouse
     */
    @PostMapping("")
    public ResponseEntity<?> createWarehouse(@RequestBody WarehouseRequestDTO warehouseRequestDTO) {
        WarehouseResponseDTO warehouseResponseDTO = warehouseService.createWarehouse(warehouseRequestDTO);
        return new ResponseEntity<>(warehouseResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Get all warehouses
     *
     * @return List of WarehouseResponseDTO
     */
    public ResponseEntity<List<WarehouseResponseDTO>> getAll() {
        return ResponseEntity.ok(warehouseService.getAll());
    }
}
