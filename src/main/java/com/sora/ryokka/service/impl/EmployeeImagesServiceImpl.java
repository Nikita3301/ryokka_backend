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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("ryokka-359c6.appspot.com", fileName); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        InputStream inputStream = EmployeeImagesServiceImpl.class.getClassLoader().getResourceAsStream("firebase/firebase_config.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/ryokka-359c6.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String uploadEmployeeImage(MultipartFile file, Long employeeId) {
        try {
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File tempFile = this.convertToFile(file, fileName);
            String imageUrl = this.uploadFile(tempFile, fileName);
            tempFile.delete();

            updateEmployeeImage(employeeId, imageUrl);

            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Image upload failed", e);
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
