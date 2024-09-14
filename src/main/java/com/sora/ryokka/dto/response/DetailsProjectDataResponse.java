package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public record DetailsProjectDataResponse(
        @JsonProperty("projectId") Long projectId,
        @JsonProperty("projectName") String projectName,
        @JsonProperty("startDate") Date startDate,
        @JsonProperty("endDate") Date endDate,
        @JsonProperty("projectStatus") String projectStatus,
        @JsonProperty("projectLocation") String projectLocation,
        @JsonProperty("projectDescription") String projectDescription,
        @JsonProperty("projectBudget") Double projectBudget,
        @JsonProperty("invoices") List<InvoiceDataResponse> invoices,
        @JsonProperty("client") ClientDataResponse client,
        @JsonProperty("employees") List<EmployeeDataResponse> employees,
        @JsonProperty("resources") List<ResourceDataResponse> resources,
        @JsonProperty("mainImageUrl") String mainImageUrl

) {
    public DetailsProjectDataResponse(Project project) {
        this(
                project.getProjectId(),
                project.getProjectName(),
                project.getStartDate(),
                project.getEndDate(),
                project.getProjectStatus().getDescription(),
                project.getProjectLocation(),
                project.getProjectDescription(),
                project.getProjectBudget(),
                convertInvoices(project.getInvoices()),
                new ClientDataResponse(project.getClient()),
                convertEmployees(project.getProjectEmployees()),
                convertResources(project.getResources()),
                getMainImageUrl(project)
        );
    }

    private static List<InvoiceDataResponse> convertInvoices(List<Invoice> invoices) {
        List<InvoiceDataResponse> dtoList = new ArrayList<>();
        for (Invoice invoice : invoices) {
            dtoList.add(new InvoiceDataResponse(invoice));
        }
        return dtoList;
    }

    private static List<EmployeeDataResponse> convertEmployees(List<ProjectEmployee> projectEmployees) {
        List<EmployeeDataResponse> dtoList = new ArrayList<>();
        for (ProjectEmployee pe : projectEmployees) {
            dtoList.add(new EmployeeDataResponse(pe.getEmployee()));
        }
        return dtoList;
    }

    private static List<ResourceDataResponse> convertResources(List<Resource> resources) {
        List<ResourceDataResponse> dtoList = new ArrayList<>();
        for (Resource resource : resources) {
            dtoList.add(new ResourceDataResponse(resource));
        }
        return dtoList;
    }

    private static String getMainImageUrl(Project project) {
        for (ProjectImage projectImage : project.getProjectImages()) {
            if (projectImage.getIsMainImage()) {
                return projectImage.getUrl();
            }
        }
        return null;
    }
}
