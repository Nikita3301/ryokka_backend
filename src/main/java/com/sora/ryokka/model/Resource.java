package com.sora.ryokka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Resources")
@Getter
@Setter
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resource_id;

    @Column(name = "resource_name", nullable = false)
    private String resourceName;

    @Column(name = "resource_description")
    private String resourceDescription;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "unit_cost")
    private Double unitCost;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "general_price")
    private Double generalPrice;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

}
