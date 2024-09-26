package com.sora.ryokka.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ProjectEmployeeId implements Serializable {

    private Long projectId;
    private int employeeId;
}
