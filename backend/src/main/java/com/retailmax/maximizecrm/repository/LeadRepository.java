package com.retailmax.maximizecrm.repository;

import com.retailmax.maximizecrm.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}