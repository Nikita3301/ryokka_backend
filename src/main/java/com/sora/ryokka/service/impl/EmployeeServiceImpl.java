package com.sora.ryokka.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.sora.ryokka.dto.request.CreateEmployeeRequest;
import com.sora.ryokka.dto.request.UpdateEmployeeRequest;
import com.sora.ryokka.dto.response.EmployeeDataResponse;
import com.sora.ryokka.model.Employee;
import com.sora.ryokka.repository.EmployeeRepository;
import com.sora.ryokka.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> createEmployee(MultipartFile imageFile, CreateEmployeeRequest createEmployeeRequest) {
        try {
            Employee newEmployee = new Employee();
            String fileName = imageFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File tempFile = this.convertToFile(imageFile, fileName);
            String imageUrl = this.uploadFile(tempFile, fileName);

            newEmployee.setFirstName(createEmployeeRequest.firstName());
            newEmployee.setLastName(createEmployeeRequest.lastName());
            newEmployee.setJobTitle(createEmployeeRequest.jobTitle());
            newEmployee.setPhoneNumber(createEmployeeRequest.phoneNumber());
            newEmployee.setEmail(createEmployeeRequest.email());
            newEmployee.setImageUrl(imageUrl);
            employeeRepository.save(newEmployee);
            return ResponseEntity.ok(newEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create employee: " + e.getMessage());
        }

    }

    @Override
    public ResponseEntity<EmployeeDataResponse> updateEmployee(MultipartFile imageFile, UpdateEmployeeRequest updateEmployeeRequest) {
        Optional<Employee> existingEmployee = employeeRepository.findById(updateEmployeeRequest.employeeId());
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setFirstName(updateEmployeeRequest.firstName());
            employee.setLastName(updateEmployeeRequest.lastName());
            employee.setJobTitle(updateEmployeeRequest.jobTitle());
            employee.setPhoneNumber(updateEmployeeRequest.phoneNumber());
            employee.setEmail(updateEmployeeRequest.email());


            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    String fileName = imageFile.getOriginalFilename();
                    fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
                    File tempFile = this.convertToFile(imageFile, fileName);
                    String imageUrl = this.uploadFile(tempFile, fileName);
                    employee.setImageUrl(imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            employeeRepository.save(employee);
            return ResponseEntity.ok(new EmployeeDataResponse(employee));
        }
        throw new RuntimeException("Employee not found with id " + updateEmployeeRequest.employeeId());
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public String uploadEmployeeImage(MultipartFile file, Integer employeeId) {
        try {
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File tempFile = this.convertToFile(file, fileName);
            String imageUrl = this.uploadFile(tempFile, fileName);

            updateEmployeeImage(employeeId, imageUrl);

            return imageUrl;
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }


    @Override
    public void updateEmployeeImage(Integer employeeId, String imageUrl) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setImageUrl(imageUrl);
        employeeRepository.save(employee);
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

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("ryokka-359c6.appspot.com", fileName); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = EmployeeImagesServiceImpl.class.getClassLoader().getResourceAsStream("firebase/firebase_config.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/ryokka-359c6.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

}
