package com.sora.ryokka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table
public class ProjectImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "is_main_image")
    private Boolean isMainImage = false;

    @Column(name = "date")
    private LocalDate date;

}
