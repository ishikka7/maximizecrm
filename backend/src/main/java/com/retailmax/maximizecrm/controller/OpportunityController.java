package com.retailmax.maximizecrm.controller;

import com.retailmax.maximizecrm.entity.Contact;
import com.retailmax.maximizecrm.entity.Opportunity;
import com.retailmax.maximizecrm.repository.ContactRepository;
import com.retailmax.maximizecrm.repository.LeadRepository;
import com.retailmax.maximizecrm.repository.OpportunityRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@CrossOrigin(origins = "http://localhost:4200")
public class OpportunityController {
    private static final List<String> STAGES =
            List.of("Prospecting", "Proposal", "Negotiation", "Won", "Lost");

    private final OpportunityRepository repo;
    private final LeadRepository leadRepo;
    private final ContactRepository contactRepo;

    public OpportunityController(OpportunityRepository repo, LeadRepository leadRepo,
                                 ContactRepository contactRepo) {
        this.repo = repo;
        this.leadRepo = leadRepo;
        this.contactRepo = contactRepo;
    }

    @GetMapping
    public List<Opportunity> all() { return repo.findAll(); }

    @PostMapping
    public Opportunity create(@Valid @RequestBody Opportunity o) {
        o.setId(null);
        if (o.getStage() == null || !STAGES.contains(o.getStage())) o.setStage("Prospecting");
        return repo.save(o);
    }

    @PutMapping("/{id}/stage")
    public Opportunity moveStage(@PathVariable Long id, @RequestParam String stage) {
        if (!STAGES.contains(stage)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid stage");
        }
        Opportunity o = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deal not found"));
        o.setStage(stage);
        Opportunity saved = repo.save(o);
        if ("Won".equals(stage) && saved.getLeadId() != null) {
            convertToCustomer(saved);
        }
        return saved;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }

    // AUTOMATION RULE: a won deal turns its lead into a customer (or adds to their lifetime value)
    private void convertToCustomer(Opportunity deal) {
        leadRepo.findById(deal.getLeadId()).ifPresent(lead -> {
            Contact c = contactRepo.findByLeadId(lead.getId()).orElseGet(() -> {
                Contact n = new Contact();
                n.setLeadId(lead.getId());
                n.setName(lead.getName());
                n.setEmail(lead.getEmail());
                n.setPhone(lead.getPhone());
                n.setSource(lead.getSource());
                return n;
            });
            double current = c.getLifetimeValue() == null ? 0 : c.getLifetimeValue();
            double amount = deal.getAmount() == null ? 0 : deal.getAmount();
            c.setLifetimeValue(current + amount);
            contactRepo.save(c);
        });
    }
}