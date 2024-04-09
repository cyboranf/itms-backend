package com.pink.itms.controller;

import com.pink.itms.dto.warehouse.WarehouseResponseDTO;
import com.pink.itms.service.WarehouseService;
import org.springframework.http.ResponseEntity;
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
     * Get all warehouses
     *
     * @return List of WarehouseResponseDTO
     */
    public ResponseEntity<List<WarehouseResponseDTO>> getAll() {
        return ResponseEntity.ok(warehouseService.getAll());
    }
}
