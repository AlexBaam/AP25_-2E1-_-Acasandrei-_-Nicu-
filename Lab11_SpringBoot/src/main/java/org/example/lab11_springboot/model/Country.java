package org.example.lab11_springboot.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String code;

    @Column(name= "continent")
    private Integer continentId;

    public Country() {}

    public Country(String name, String code, Integer continentId) {
        this.name = name;
        this.code = code;
        this.continentId = continentId;
    }
}
