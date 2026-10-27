package com.retailmax.maximizecrm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign_logs")
@Data
public class CampaignLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long campaignId;
    private Long leadId;
    private String leadName;
    private String email;
    @Column(length = 1000) private String message;
    private LocalDateTime sentAt = LocalDateTime.now();
}