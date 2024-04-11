package com.pink.itms.service;

import com.pink.itms.dto.taskType.TaskTypeRequestDTO;
import com.pink.itms.dto.taskType.TaskTypeResponseDTO;
import com.pink.itms.dto.warehouse.WarehouseRequestDTO;
import com.pink.itms.dto.warehouse.WarehouseResponseDTO;
import com.pink.itms.exception.taskType.ExistingNameException;
import com.pink.itms.mapper.WarehouseMapper;
import com.pink.itms.model.Product;
import com.pink.itms.model.TaskType;
import com.pink.itms.model.Warehouse;
import com.pink.itms.repository.ProductRepository;
import com.pink.itms.repository.WarehouseRepository;
import com.pink.itms.validation.WarehouseValidator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
     * Edits existing warehouse entity pointed by given id
     *
     * @param id                  of entity to edit
     * @param warehouseRequestDTO values to replace with
     * @return {@link WarehouseResponseDTO} - response from changed entity
     */
    @Transactional
    public WarehouseResponseDTO editWarehouse(Long id, WarehouseRequestDTO warehouseRequestDTO) {
        Optional<Warehouse> existingWarehouse = warehouseRepository.findById(id);

        if (existingWarehouse.isEmpty()) {
            throw new EntityNotFoundException("Warehouse with id " + id + " does not exist.");
        }

        Warehouse warehouse = existingWarehouse.get();
        warehouse.setBuilding(warehouseRequestDTO.getBuilding());
        warehouse.setZone(warehouseRequestDTO.getZone());
        warehouse.setSpaceId(warehouseRequestDTO.getSpaceId());
        warehouse.setSpaceHeight(warehouseRequestDTO.getSpaceHeight());
        warehouse.setSpaceWidth(warehouseRequestDTO.getSpaceWidth());
        warehouse.setSpaceLength(warehouseRequestDTO.getSpaceLength());

        return warehouseMapper.toDTO(warehouse);
    }



    /**
     * Get all warehouses
     *
     * @return List of WarehouseResponseDTO
     */
    public List<WarehouseResponseDTO> getAll() {
        return warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::toDTO)
                .toList();
    }

}
