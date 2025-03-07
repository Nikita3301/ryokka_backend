package com.sora.ryokka.controller.images;

import com.sora.ryokka.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employee-images")
public class EmployeeImagesController {

    private final EmployeeService employeeService;

    public EmployeeImagesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/{employeeId}/upload")
    public ResponseEntity<String> uploadEmployeeImage(
            @PathVariable Long employeeId,
            @RequestParam("file") MultipartFile file) {
        try {
            employeeService.uploadEmployeeImage(file, employeeId);
            return ResponseEntity.ok("Image uploaded successfully.");
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load image: " + e.getMessage());
        }

    }

    @PostMapping("/{employeeId}/updateImage")
    public ResponseEntity<String> updateEmployeeImage(
            @PathVariable Long employeeId,
            @RequestParam String imageUrl) {
        try {
        employeeService.updateEmployeeImage(employeeId, imageUrl);
        return ResponseEntity.ok("Employee image updated successfully");
    }catch (Exception e) {
        return ResponseEntity.status(500).body("Failed to update image: " + e.getMessage());
    }
    }
}
