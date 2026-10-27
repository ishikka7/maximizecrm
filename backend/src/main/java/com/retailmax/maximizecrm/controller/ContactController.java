package com.retailmax.maximizecrm.controller;

import com.retailmax.maximizecrm.entity.Contact;
import com.retailmax.maximizecrm.repository.ContactRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactController {
    private final ContactRepository repo;

    public ContactController(ContactRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Contact> all() { return repo.findAll(); }
}