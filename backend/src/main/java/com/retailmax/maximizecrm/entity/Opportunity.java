package com.retailmax.maximizecrm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "opportunities")
@Data
public class Opportunity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String name;
    private Long leadId;
    private String leadName;
    private Double amount = 0.0;
    private String stage = "Prospecting";
    private LocalDateTime createdAt = LocalDateTime.now();
}