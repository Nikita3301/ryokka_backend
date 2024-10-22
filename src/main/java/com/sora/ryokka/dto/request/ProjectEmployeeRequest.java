package com.sora.ryokka.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ProjectEmployeeRequest {

    private Long employeeId;
    private Long projectId;
    private String projectRole;
    private Date startDate;
    private Date endDate;


}

