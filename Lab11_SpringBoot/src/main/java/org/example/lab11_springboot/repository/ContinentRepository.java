package org.example.lab11_springboot.repository;

import org.example.lab11_springboot.model.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentRepository extends JpaRepository<Continent, Long> {
}
