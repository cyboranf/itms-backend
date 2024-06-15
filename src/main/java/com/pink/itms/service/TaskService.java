package com.pink.itms.service;

import com.pink.itms.dto.task.TaskRequestDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.exception.product.ProductNotFoundException;
import com.pink.itms.exception.task.TaskNotFoundException;
import com.pink.itms.exception.warehouse.WarehouseNotFoundException;
import com.pink.itms.mapper.TaskMapper;
import com.pink.itms.model.*;
import com.pink.itms.repository.ProductRepository;
import com.pink.itms.repository.TaskRepository;
import com.pink.itms.repository.WarehouseRepository;
import com.pink.itms.validation.TaskValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskValidator taskValidator;
    private final TaskMapper taskMapper;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    /*
    1 - admin i manager
    2 - warehouseman
    3 - printer
     */
    private final int[][] taskState = {
            {2, 1},
            {2, 1},
            {2, 1},
            {2, 3, 2, 1},
            {1},
            {1},
            {1}
    };

    public TaskService(TaskRepository taskRepository, TaskValidator taskValidator, TaskMapper taskMapper, ProductRepository productRepository, WarehouseRepository warehouseRepository) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
        this.taskMapper = taskMapper;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * checks if given role is assigned in this state to advance task to next stage
     *
     * @param role role of user trying to advance a task
     * @param task task to be advanced
     * @return if user has authority to advance this task
     */
    private boolean isAuthorised(String role, Task task) {
        if (role.equals("Admin") || role.equals("Manager")) return true;

        switch (task.getState()) {
            case 2:
                if (role.equals("Warehouseman")) return true;
                break;
            case 3:
                if (role.equals("Printer")) return true;
        }

        return false;
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        taskValidator.validateCreate(taskRequestDTO);

        Task task = taskMapper.toEntity(taskRequestDTO);
        task.setCreationDate(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    public TaskResponseDTO getTaskById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task with id = " + id + " does not exist"));
        return taskMapper.toDto(task);
    }

    public void deleteTask(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Task with id " + id + " doesn't exist."));
        task.setIsActive(false);
        taskRepository.save(task);
    }

    /**
     * Get all tasks
     *
     * @return
     */
    public List<TaskResponseDTO> getAll() {
        return taskRepository.findAllByIsActiveTrue()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public List<TaskResponseDTO> getFilteredTasks(Integer state, Integer priority, Long userId, Long taskId) {
        List<Task> tasks = taskRepository.findAll();

        if (state != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getState().equals(state))
                    .collect(Collectors.toList());
        }
        if (priority != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getPriority().equals(priority))
                    .collect(Collectors.toList());
        }
        if (userId != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getUsers().stream().anyMatch(user -> user.getId().equals(userId)))
                    .collect(Collectors.toList());
        }
        if (taskId != null) {
            tasks = tasks.stream()
                    .filter(task -> task.getUsers().stream().anyMatch(user -> task.getId().equals(taskId)))
                    .collect(Collectors.toList());
        }

        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param taskId
     * @param taskRequestDTO
     * @return TaskResponseDTO
     */
    public TaskResponseDTO editTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        Task task = taskValidator.validateUpdate(taskId, taskRequestDTO);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    /**
     * attaches product by given id to task of given id if said connection already exists do nothing.
     *
     * @param taskId id of task to be attached
     * @param productId id of product to be attached
     */
    public void attachProduct(Long taskId, Long productId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task Not Found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        Set<Product> productSet = task.getProducts();
        productSet.add(product);
        task.setProducts(productSet);

        taskRepository.save(task);

    }

    /**
     * attaches warehouse by given id to task of given id if said connection already exists do nothing
     *
     * @param taskId id of task to be attached
     * @param warehouseId id of warehouse to be attached
     */
    public void attachWarehouse(Long taskId, Long warehouseId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task Not Found"));
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new WarehouseNotFoundException("Warehouse Not Found"));

        Set<Warehouse> warehouseSet = task.getWarehouses();
        warehouseSet.add(warehouse);
        task.setWarehouses(warehouseSet);

        taskRepository.save(task);

    }

    /**
     * Get all tasks by user id
     * @param userId
     * @return
     */
    public List<TaskResponseDTO> getAllTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findAllByUsers_IdAndIsActiveTrue(userId);
        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Changes task state to next, if state is already on last state, or state is on -1 then returns with -1
     *
     * @param taskId id of task to be changed
     * @return {@link TaskResponseDTO} - id of task to be changed
     */
    public TaskResponseDTO nextStage(Long taskId, String role) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task Not Found!"));
        TaskType type = task.getType();

        if (!isAuthorised(role, task)) throw new RuntimeException("User not authorised!");
        int state = (task.getState() + 1);

        if (state >= taskState[type.getId().intValue()].length || state == 0) {
            task.setState(-1);
        } else {
            task.setState(state);
        }

        taskRepository.save(task);
        return taskMapper.toDto(task);
    }

}
