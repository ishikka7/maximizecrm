package com.retailmax.maximizecrm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "lead_status_history")
@Data
public class LeadStatusHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long leadId;
    private String oldStatus;   // null means the lead was just created
    private String newStatus;
    private LocalDateTime changedAt = LocalDateTime.now();
}