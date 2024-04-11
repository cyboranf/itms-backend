package com.pink.itms.controller;

import com.pink.itms.dto.product.ProductResponseDTO;
import com.pink.itms.dto.taskType.TaskTypeRequestDTO;
import com.pink.itms.dto.taskType.TaskTypeResponseDTO;
import com.pink.itms.dto.warehouse.WarehouseRequestDTO;
import com.pink.itms.dto.warehouse.WarehouseResponseDTO;
import com.pink.itms.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param id
     * @return {@link WarehouseResponseDTO} - response from edit warehouse
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editWarehouse(@PathVariable long id, @RequestBody WarehouseRequestDTO warehouseRequestDTO) {
        try {
            WarehouseResponseDTO warehouseResponseDTO = warehouseService.editWarehouse(id, warehouseRequestDTO);
            return new ResponseEntity<>(warehouseResponseDTO, HttpStatus.OK);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * @param id
     * @return {@link WarehouseResponseDTO} - response from deleted warehouse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable long id) {
        try {
            warehouseService.deleteWarehouse(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
