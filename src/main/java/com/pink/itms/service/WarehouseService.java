package com.pink.itms.service;

import com.pink.itms.dto.warehouse.WarehouseRequestDTO;
import com.pink.itms.dto.warehouse.WarehouseResponseDTO;
import com.pink.itms.mapper.WarehouseMapper;
import com.pink.itms.model.Warehouse;
import com.pink.itms.repository.WarehouseRepository;
import com.pink.itms.validation.WarehouseValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final WarehouseValidator warehouseValidator;

    //TODO: Back to other services and implement the methods to getAll() entities

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper, WarehouseValidator warehouseValidator) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
        this.warehouseValidator = warehouseValidator;
    }

    /**
     * creates and saves warehouse entity
     *
     * @param warehouseRequestDTO warehouse to create
     * @return {@link WarehouseResponseDTO} - response from created warehouse
     */
    public WarehouseResponseDTO createWarehouse(WarehouseRequestDTO warehouseRequestDTO) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseRequestDTO);
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return warehouseMapper.toDTO(savedWarehouse);
    }

    /**
     * Get all warehouses
     * @return List of WarehouseResponseDTO
     */
    public List<WarehouseResponseDTO> getAll() {
        return warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::toDTO)
                .toList();
    }

}
