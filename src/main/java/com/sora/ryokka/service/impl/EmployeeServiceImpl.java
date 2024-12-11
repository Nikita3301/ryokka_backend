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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final String BUCKET_NAME = "ryokka-359c6.appspot.com"; // Replace with your bucket name
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media";

    private final EmployeeRepository employeeRepository;
    private final Storage storage;
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) throws IOException {
        this.employeeRepository = employeeRepository;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase/firebase_config.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> createEmployee(MultipartFile imageFile, CreateEmployeeRequest createEmployeeRequest) {
        try {
            Employee newEmployee = new Employee();
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(imageFile.getOriginalFilename()));
            File tempFile = convertToFile(imageFile, fileName);
            String imageUrl = uploadFile(tempFile, fileName);

            newEmployee.setFirstName(createEmployeeRequest.firstName());
            newEmployee.setLastName(createEmployeeRequest.lastName());
            newEmployee.setJobTitle(createEmployeeRequest.jobTitle());
            newEmployee.setPhoneNumber(createEmployeeRequest.phoneNumber());
            newEmployee.setEmail(createEmployeeRequest.email());
            newEmployee.setImageUrl(imageUrl);

            employeeRepository.save(newEmployee);
            logger.info("Employee created successfully: {}", newEmployee);

            return ResponseEntity.ok(new EmployeeDataResponse(newEmployee));
        } catch (Exception e) {
            logger.error("Failed to create employee", e);
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
                    String fileName = UUID.randomUUID().toString().concat(this.getExtension(imageFile.getOriginalFilename()));
                    File tempFile = convertToFile(imageFile, fileName);
                    String imageUrl = uploadFile(tempFile, fileName);
                    employee.setImageUrl(imageUrl);
                    deleteTempFile(tempFile);
                } catch (IOException e) {
                    logger.error("Failed to update employee image", e);
                    throw new RuntimeException("Failed to update employee image", e);
                }
            }

            employeeRepository.save(employee);
            logger.info("Employee updated successfully: {}", employee);
            return ResponseEntity.ok(new EmployeeDataResponse(employee));
        }
        logger.warn("Employee not found with id {}", updateEmployeeRequest.employeeId());
        throw new RuntimeException("Employee not found with id " + updateEmployeeRequest.employeeId());
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        logger.info("Employee deleted with id {}", id);
    }

    @Override
    public String uploadEmployeeImage(MultipartFile file, Long employeeId) {
        try {
            String fileName = UUID.randomUUID().toString().concat(this.getExtension(file.getOriginalFilename()));
            File tempFile = convertToFile(file, fileName);
            String imageUrl = uploadFile(tempFile, fileName);
            updateEmployeeImage(employeeId, imageUrl);
            deleteTempFile(tempFile);
            return imageUrl;
        } catch (Exception e) {
            logger.error("Image upload failed", e);
            throw new RuntimeException("Image upload failed", e);
        }
    }

    @Override
    public void updateEmployeeImage(Long employeeId, String imageUrl) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setImageUrl(imageUrl);
        employeeRepository.save(employee);
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        tempFile.deleteOnExit();
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, BUCKET_NAME, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private void deleteTempFile(File tempFile) throws IOException {
        if (tempFile.exists()) {
            boolean deleted = Files.deleteIfExists(tempFile.toPath());
            if (!deleted) {
                System.err.println("Failed to delete temporary file: " + tempFile.getAbsolutePath());
            }
        }
    }

}
