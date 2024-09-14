package com.sora.ryokka.service;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeImagesService {
    String uploadEmployeeImage(MultipartFile file, Integer employeeId);
    void updateEmployeeImage(Integer employeeId, String imageUrl);
}