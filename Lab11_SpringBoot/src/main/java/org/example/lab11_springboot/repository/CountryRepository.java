package org.example.lab11_springboot.repository;

import org.example.lab11_springboot.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findByContinentId(Integer continentId);
}
