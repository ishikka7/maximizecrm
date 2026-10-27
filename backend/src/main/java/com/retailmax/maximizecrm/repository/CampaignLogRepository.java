package com.retailmax.maximizecrm.repository;

import com.retailmax.maximizecrm.entity.CampaignLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampaignLogRepository extends JpaRepository<CampaignLog, Long> {
    List<CampaignLog> findByCampaignIdOrderByIdAsc(Long campaignId);
}