package com.retailmax.maximizecrm.controller;

import com.retailmax.maximizecrm.entity.Opportunity;
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

    public OpportunityController(OpportunityRepository repo) { this.repo = repo; }

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
        return repo.save(o);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}