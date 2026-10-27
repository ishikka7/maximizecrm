package com.retailmax.maximizecrm.repository;

import com.retailmax.maximizecrm.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByLeadId(Long leadId);
}