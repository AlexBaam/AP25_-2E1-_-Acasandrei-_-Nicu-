package org.example.lab11_springboot.repository;

import org.example.lab11_springboot.model.Border;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorderRepository extends JpaRepository<Border, Long> {
    List<Border> findAll();
}
