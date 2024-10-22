package com.sora.ryokka.service;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeImagesService {
    String uploadEmployeeImage(MultipartFile file, Long employeeId);
    void updateEmployeeImage(Long employeeId, String imageUrl);
}