package com.sora.ryokka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Projects")
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "project_status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Column(name = "project_location")
    private String projectLocation;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_budget")
    private Double projectBudget;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Resource> resources;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectEmployee> projectEmployees;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Invoice> invoices;
}