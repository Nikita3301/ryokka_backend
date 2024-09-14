package com.sora.ryokka.service;

import com.sora.ryokka.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(int id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(int id, Employee employee);
    void deleteEmployee(int id);
    String uploadEmployeeImage(MultipartFile file, Integer employeeId);
    void updateEmployeeImage(Integer employeeId, String imageUrl);
}
