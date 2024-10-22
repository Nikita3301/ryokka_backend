package com.sora.ryokka.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Invoices")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    @Column(name = "invoice_name")
    private String invoiceName;

    @Column(name = "invoice_description")
    private String invoiceDescription;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "invoice_status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
