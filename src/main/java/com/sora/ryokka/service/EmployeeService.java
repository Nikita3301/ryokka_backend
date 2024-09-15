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
    Optional<Employee> getEmployeeById(int id);
    ResponseEntity<?> createEmployee(MultipartFile imageFile, CreateEmployeeRequest createEmployeeRequest);
    ResponseEntity<EmployeeDataResponse> updateEmployee(MultipartFile imageFile, UpdateEmployeeRequest updateEmployeeRequest);
    void deleteEmployee(int id);
    String uploadEmployeeImage(MultipartFile file, Integer employeeId);
    void updateEmployeeImage(Integer employeeId, String imageUrl);
}
