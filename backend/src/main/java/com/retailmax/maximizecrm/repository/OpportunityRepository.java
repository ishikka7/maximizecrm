package com.retailmax.maximizecrm.repository;

import com.retailmax.maximizecrm.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    boolean existsByLeadId(Long leadId);
}