package org.example.lab11_springboot.controller;

import org.example.lab11_springboot.model.Continent;
import org.example.lab11_springboot.repository.ContinentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/continents")
public class ContinentController {

    private final ContinentRepository continentRepo;

    public ContinentController(ContinentRepository continentRepo) {
        this.continentRepo = continentRepo;
    }

    @GetMapping
    public List<Continent> getAllContinents() {
        return continentRepo.findAll();
    }
}