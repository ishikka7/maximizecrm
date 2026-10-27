package com.retailmax.maximizecrm.controller;

import com.retailmax.maximizecrm.entity.Campaign;
import com.retailmax.maximizecrm.entity.CampaignLog;
import com.retailmax.maximizecrm.entity.Lead;
import com.retailmax.maximizecrm.repository.CampaignLogRepository;
import com.retailmax.maximizecrm.repository.CampaignRepository;
import com.retailmax.maximizecrm.repository.LeadRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:4200")
public class CampaignController {
    private final CampaignRepository campaignRepo;
    private final CampaignLogRepository logRepo;
    private final LeadRepository leadRepo;

    public CampaignController(CampaignRepository campaignRepo, CampaignLogRepository logRepo,
                              LeadRepository leadRepo) {
        this.campaignRepo = campaignRepo;
        this.logRepo = logRepo;
        this.leadRepo = leadRepo;
    }

    @GetMapping
    public List<Campaign> all() { return campaignRepo.findAll(); }

    @PostMapping
    public Campaign create(@Valid @RequestBody Campaign c) {
        c.setId(null);
        c.setStatus("Draft");
        c.setSentCount(0);
        c.setSentAt(null);
        return campaignRepo.save(c);
    }

    // Segment the audience, "send" the email (simulated) and log every recipient
    @PostMapping("/{id}/send")
    public Campaign send(@PathVariable Long id) {
        Campaign c = campaignRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found"));
        if ("Sent".equals(c.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campaign already sent");
        }
        String template = c.getMessage() == null ? "" : c.getMessage();
        int count = 0;
        for (Lead l : leadRepo.findAll()) {
            if (!matches(c.getSegment(), l)) continue;
            if (l.getEmail() == null || l.getEmail().isBlank()) continue;
            CampaignLog log = new CampaignLog();
            log.setCampaignId(c.getId());
            log.setLeadId(l.getId());
            log.setLeadName(l.getName());
            log.setEmail(l.getEmail());
            log.setMessage(template.replace("{name}", l.getName()));
            logRepo.save(log);
            count++;
        }
        c.setStatus("Sent");
        c.setSentCount(count);
        c.setSentAt(LocalDateTime.now());
        return campaignRepo.save(c);
    }

    @GetMapping("/{id}/logs")
    public List<CampaignLog> logs(@PathVariable Long id) {
        return logRepo.findByCampaignIdOrderByIdAsc(id);
    }

    private boolean matches(String segment, Lead l) {
        int score = l.getScore() == null ? 0 : l.getScore();
        String seg = segment == null ? "ALL" : segment;
        return switch (seg) {
            case "HOT" -> score >= 60;
            case "QUALIFIED" -> "Qualified".equals(l.getStatus());
            case "REFERRAL" -> "Referral".equals(l.getSource());
            case "WEBSITE" -> "Website".equals(l.getSource());
            case "STORE_VISIT" -> "Store Visit".equals(l.getSource());
            default -> true;
        };
    }
}