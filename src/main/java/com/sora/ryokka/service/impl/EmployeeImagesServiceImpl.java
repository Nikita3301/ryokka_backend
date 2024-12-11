package com.sora.ryokka.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.sora.ryokka.model.Employee;
import com.sora.ryokka.repository.EmployeeRepository;
import com.sora.ryokka.service.EmployeeImagesService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class EmployeeImagesServiceImpl implements EmployeeImagesService {

    private static final String BUCKET_NAME = "ryokka-359c6.appspot.com";
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media";
    private final Storage storage;
    private final EmployeeRepository employeeRepository;

    public EmployeeImagesServiceImpl(EmployeeRepository employeeRepository) throws IOException {
        this.employeeRepository = employeeRepository;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase/firebase_config.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return String.format(DOWNLOAD_URL, BUCKET_NAME, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String uploadEmployeeImage(MultipartFile file, Long employeeId) {
        String imageUrl = null;
        File tempFile = null;
        try {
            String fileName = file.getOriginalFilename();
            if (fileName != null) {
                fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
                tempFile = new File(fileName);
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(file.getBytes());
                }
                imageUrl = this.uploadFile(tempFile, fileName);
                updateEmployeeImage(employeeId, imageUrl);
            }
            return imageUrl;
        } catch (IOException e) {
            System.err.println("Error uploading employee image: " + e.getMessage());
            throw new RuntimeException("Image upload failed", e);
        }finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Override
    public void updateEmployeeImage(Long employeeId, String imageUrl) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setImageUrl(imageUrl);
        employeeRepository.save(employee);
    }
}
