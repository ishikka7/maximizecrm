package com.retailmax.maximizecrm.repository;

import com.retailmax.maximizecrm.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}