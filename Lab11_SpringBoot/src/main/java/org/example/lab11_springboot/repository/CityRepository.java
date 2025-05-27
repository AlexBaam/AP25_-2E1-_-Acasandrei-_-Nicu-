package org.example.lab11_springboot.repository;

import org.example.lab11_springboot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
}
