package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.CreateEmployeeRequest;
import com.sora.ryokka.dto.request.UpdateEmployeeRequest;
import com.sora.ryokka.dto.response.EmployeeDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Employee;
import com.sora.ryokka.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeDataResponse>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDataResponse> response = new ArrayList<>();

        for (Employee employee : employees) {
            response.add(new EmployeeDataResponse(employee));
        }

        return ResponseEntity.ok(response);
    }

    // Get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDataResponse> getEmployeeById(@PathVariable("id") int id) {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            return ResponseEntity.ok().body(new EmployeeDataResponse(employee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new employee
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestParam("imageFile") MultipartFile imageFile, @ModelAttribute CreateEmployeeRequest createEmployeeRequest) {
        return employeeService.createEmployee(imageFile, createEmployeeRequest);
    }

    // Update an employee
    @PutMapping
    public ResponseEntity<EmployeeDataResponse> updateEmployee(@RequestParam(value="imageFile", required = false) MultipartFile imageFile, @ModelAttribute UpdateEmployeeRequest updateEmployeeRequest) {
        return employeeService.updateEmployee(imageFile, updateEmployeeRequest);
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }




}
