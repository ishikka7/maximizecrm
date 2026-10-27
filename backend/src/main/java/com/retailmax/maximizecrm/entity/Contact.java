package com.retailmax.maximizecrm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
@Data
public class Contact {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long leadId;
    private String name;
    private String email;
    private String phone;
    private String source;
    private Double lifetimeValue = 0.0;
    private LocalDateTime createdAt = LocalDateTime.now();
}