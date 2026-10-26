package com.retailmax.maximizecrm.controller;

import com.retailmax.maximizecrm.entity.Lead;
import com.retailmax.maximizecrm.entity.LeadStatusHistory;
import com.retailmax.maximizecrm.repository.LeadRepository;
import com.retailmax.maximizecrm.repository.LeadStatusHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "http://localhost:4200")
public class LeadController {
    private final LeadRepository repo;
    private final LeadStatusHistoryRepository historyRepo;

    public LeadController(LeadRepository repo, LeadStatusHistoryRepository historyRepo) {
        this.repo = repo;
        this.historyRepo = historyRepo;
    }

    @GetMapping
    public List<Lead> all() { return repo.findAll(); }

    @PostMapping
    public Lead create(@Valid @RequestBody Lead l) {
        l.setId(null);
        Lead saved = repo.save(l);
        logStatus(saved.getId(), null, saved.getStatus());
        return saved;
    }

    @PutMapping("/{id}")
    public Lead update(@PathVariable Long id, @Valid @RequestBody Lead l) {
        Lead existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lead not found"));
        String oldStatus = existing.getStatus();

        existing.setName(l.getName());
        existing.setEmail(l.getEmail());
        existing.setPhone(l.getPhone());
        existing.setSource(l.getSource());
        existing.setStatus(l.getStatus());

        Lead saved = repo.save(existing);
        if (!Objects.equals(oldStatus, saved.getStatus())) {
            logStatus(saved.getId(), oldStatus, saved.getStatus());
        }
        return saved;
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        List<Lead> all = repo.findAll();

        Map<String, Long> byStatus = new LinkedHashMap<>();
        for (String s : List.of("New", "Contacted", "Qualified", "Lost")) byStatus.put(s, 0L);
        all.forEach(l -> byStatus.merge(l.getStatus() == null ? "Unknown" : l.getStatus(), 1L, Long::sum));

        Map<String, Long> bySource = all.stream().collect(Collectors.groupingBy(
                l -> l.getSource() == null ? "Unknown" : l.getSource(),
                LinkedHashMap::new, Collectors.counting()));

        double avgScore = all.stream().mapToInt(l -> l.getScore() == null ? 0 : l.getScore()).average().orElse(0);
        double conversion = all.isEmpty() ? 0 : byStatus.get("Qualified") * 100.0 / all.size();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", all.size());
        result.put("avgScore", Math.round(avgScore * 10) / 10.0);
        result.put("conversionRate", Math.round(conversion * 10) / 10.0);
        result.put("byStatus", byStatus);
        result.put("bySource", bySource);
        return result;
    }
    
    @GetMapping("/{id}/history")
    public List<LeadStatusHistory> history(@PathVariable Long id) {
        return historyRepo.findByLeadIdOrderByChangedAtDesc(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        historyRepo.deleteByLeadId(id);
        repo.deleteById(id);
    }

    private void logStatus(Long leadId, String oldStatus, String newStatus) {
        LeadStatusHistory h = new LeadStatusHistory();
        h.setLeadId(leadId);
        h.setOldStatus(oldStatus);
        h.setNewStatus(newStatus);
        historyRepo.save(h);
    }
}