package com.system_gestion_soutenance.api.coordinator.unavailability.controller;

import com.system_gestion_soutenance.api.coordinator.unavailability.entity.Unavailability;
import com.system_gestion_soutenance.api.coordinator.unavailability.repository.UnavailabilityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coordinator/unavailability")
public class UnavailabilityController {

    private final UnavailabilityRepository repository;

    public UnavailabilityController(UnavailabilityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Unavailability> findAll() {
        return repository.findAll();
    }
}
