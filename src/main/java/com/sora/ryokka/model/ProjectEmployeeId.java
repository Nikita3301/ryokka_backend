package com.sora.ryokka.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEmployeeId implements Serializable {
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "employee_id")
    private Long employeeId;
}
