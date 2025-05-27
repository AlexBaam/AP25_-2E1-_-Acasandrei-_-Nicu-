package org.example.lab11_springboot.controller;


import org.example.lab11_springboot.model.Country;
import org.example.lab11_springboot.repository.CountryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryRepository countryRepo;

    public CountryController(CountryRepository countryRepo) {
        this.countryRepo = countryRepo;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryRepo.findAll();
    }
}
