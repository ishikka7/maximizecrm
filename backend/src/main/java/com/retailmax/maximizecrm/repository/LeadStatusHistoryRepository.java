package com.retailmax.maximizecrm.repository;

import com.retailmax.maximizecrm.entity.LeadStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeadStatusHistoryRepository extends JpaRepository<LeadStatusHistory, Long> {
    List<LeadStatusHistory> findByLeadIdOrderByChangedAtDesc(Long leadId);
    void deleteByLeadId(Long leadId);
}