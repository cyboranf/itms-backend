package com.pink.itms.controller;

import com.pink.itms.dto.product.ProductResponseDTO;
import com.pink.itms.dto.task.TaskResponseDTO;
import com.pink.itms.dto.user.UserRequestDTO;
import com.pink.itms.dto.user.UserResponseWithoutTasksDTO;
import com.pink.itms.dto.warehouse.WarehouseRequestDTO;
import com.pink.itms.dto.warehouse.WarehouseResponseDTO;
import com.pink.itms.mapper.ProductMapper;
import com.pink.itms.mapper.TaskMapper;
import com.pink.itms.mapper.UserMapper;
import com.pink.itms.mapper.WarehouseMapper;
import com.pink.itms.service.ProductService;
import com.pink.itms.service.TaskService;
import com.pink.itms.service.UserService;
import com.pink.itms.service.WarehouseService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pdf.generator.PdfGeneratorService;
import pdf.generator.model.Tasks;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PdfController {

    private final PdfGeneratorService pdfReportGenerator;
    private final UserService userService;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final TaskService taskService;

    public PdfController(PdfGeneratorService pdfReportGenerator, UserService userService, WarehouseService warehouseService, ProductService productService, TaskService taskService) {
        this.pdfReportGenerator = pdfReportGenerator;
        this.userService = userService;
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.taskService = taskService;
    }


    @GetMapping("/generate-user-report")
    public ResponseEntity<InputStreamResource> generateUserReport(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "includeTasks", required = false, defaultValue = "false") boolean includeTasks) {
        List<UserResponseWithoutTasksDTO> users = userService.getAll();

        // Filtrowanie
        if (username != null && !username.isEmpty()) {
            users = users.stream()
                    .filter(user -> user.getUsername().equalsIgnoreCase(username))
                    .collect(Collectors.toList());
        }
        if (email != null && !email.isEmpty()) {
            users = users.stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            users = users.stream()
                    .filter(user -> user.getPhoneNumber().equalsIgnoreCase(phoneNumber))
                    .collect(Collectors.toList());
        }

        List<pdf.generator.model.User> pdfUsers = UserMapper.toPdfUserList(users);

        ByteArrayInputStream bis = pdfReportGenerator.generateUserReport(pdfUsers, includeTasks);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=user-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/generate-warehouse-report")
    public ResponseEntity<InputStreamResource> generateWarehouseReport() {
        List<WarehouseResponseDTO> warehouse = warehouseService.getAll();
        List<pdf.generator.model.Warehouse> pdfWarehouse = WarehouseMapper.toPdfWarehouseList(warehouse);

        ByteArrayInputStream bis = pdfReportGenerator.generateWarehouseReport(pdfWarehouse);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=user-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/generate-items-report")
    public ResponseEntity<InputStreamResource> generateItemsReport() {
        List<ProductResponseDTO> products = productService.getAll();
        List<pdf.generator.model.Product> pdfProducts = ProductMapper.toPdfProductList(products);

        ByteArrayInputStream bis = pdfReportGenerator.generateProductReport(pdfProducts);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=user-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/generate-task-report")
    public ResponseEntity<InputStreamResource> generateTaskReport(
            @RequestParam(value = "state", required = false) Integer state,
            @RequestParam(value = "priority", required = false) Integer priority,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "includeUsers", required = false, defaultValue = "false") boolean includeUsers,
            @RequestParam(value = "includeProducts", required = false, defaultValue = "false") boolean includeProducts,
            @RequestParam(value = "includeWarehouses", required = false, defaultValue = "false") boolean includeWarehouses,
            @RequestParam(value = "includePieChart", required = false, defaultValue = "false") boolean includePieChart) {

        List<TaskResponseDTO> tasks = taskService.getFilteredTasks(state, priority, userId);
        List<Tasks> pdfTasks = TaskMapper.toPdfTaskList(tasks);

        ByteArrayInputStream bis = pdfReportGenerator.generateTaskReport(pdfTasks, includeUsers, includeProducts, includeWarehouses, includePieChart);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=task-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }



}
