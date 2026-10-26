package com.retailmax.maximizecrm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Data
public class Lead {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String name;
    @Email private String email;
    private String phone;
    private String source;
    private String status;
    private Integer score = 0;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Lead scoring rules: runs automatically on every insert/update
    @PrePersist
    @PreUpdate
    public void calculateScore() {
        int s = 0;
        if ("Referral".equals(source)) s += 30;
        else if ("Store Visit".equals(source)) s += 20;
        else if ("Website".equals(source)) s += 10;

        if (email != null && !email.isBlank()) s += 10;
        if (phone != null && !phone.isBlank()) s += 10;

        if ("Contacted".equals(status)) s += 10;
        else if ("Qualified".equals(status)) s += 30;

        this.score = s;
    }
}