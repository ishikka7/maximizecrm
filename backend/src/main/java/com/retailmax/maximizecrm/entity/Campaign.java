package com.retailmax.maximizecrm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaigns")
@Data
public class Campaign {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String name;
    @Column(length = 1000) private String message;
    private String segment;            // ALL, HOT, QUALIFIED, REFERRAL, WEBSITE, STORE_VISIT
    private String status = "Draft";   // Draft or Sent
    private Integer sentCount = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime sentAt;
}