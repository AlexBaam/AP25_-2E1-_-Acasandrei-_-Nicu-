package org.example.lab11_springboot.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

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