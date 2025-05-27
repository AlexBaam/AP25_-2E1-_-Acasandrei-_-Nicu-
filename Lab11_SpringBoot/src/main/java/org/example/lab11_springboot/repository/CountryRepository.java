package org.example.lab11_springboot.repository;

import org.example.lab11_springboot.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
