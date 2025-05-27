package org.example.lab11_springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "borders")
public class Border {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String countryCode;
    private String neighborCode;

    public Border() {}

    public Border(String countryCode, String neighborCode) {
        this.countryCode = countryCode;
        this.neighborCode = neighborCode;
    }
}