package org.example.lab11_springboot.controller;

import org.example.lab11_springboot.model.City;
import org.example.lab11_springboot.repository.CityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityRepository cityRepo;

    public CityController(CityRepository cityRepo) {
        this.cityRepo = cityRepo;
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityRepo.findAll();
    }

    @PostMapping
    public City createCity(@RequestBody City newCity) {
        return cityRepo.save(newCity);
    }

    @PutMapping("/{id}")
    public City updateCity(@RequestBody City updatedCity, @PathVariable Integer id) {
        updatedCity.setId(id);
        return cityRepo.save(updatedCity);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable long id) {
        cityRepo.deleteById(id);
    }
}
