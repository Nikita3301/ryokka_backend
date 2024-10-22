package com.sora.ryokka.service;

import com.sora.ryokka.dto.request.ProjectEmployeeRequest;

public interface ProjectEmployeeService {

    void addEmployeeToProject(ProjectEmployeeRequest request);

    void removeEmployeeFromProject(ProjectEmployeeRequest request);
}
