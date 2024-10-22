package com.sora.ryokka.service;

import com.sora.ryokka.dto.request.CreateEmployeeRequest;
import com.sora.ryokka.dto.request.UpdateEmployeeRequest;
import com.sora.ryokka.dto.response.EmployeeDataResponse;
import com.sora.ryokka.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id);
    ResponseEntity<?> createEmployee(MultipartFile imageFile, CreateEmployeeRequest createEmployeeRequest);
    ResponseEntity<EmployeeDataResponse> updateEmployee(MultipartFile imageFile, UpdateEmployeeRequest updateEmployeeRequest);
    void deleteEmployee(Long id);
    String uploadEmployeeImage(MultipartFile file, Long employeeId);
    void updateEmployeeImage(Long employeeId, String imageUrl);
}
