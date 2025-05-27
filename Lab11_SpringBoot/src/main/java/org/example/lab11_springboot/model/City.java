package org.example.lab11_springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "country")
    private Integer countryId;

    private Boolean capital;

    private Double latitude;
    private Double longitude;

    @Column(name = "is_fake")
    private Boolean isFake;

    public City() {}

    public City(String name, Integer countryId, Boolean capital, Double latitude, Double longitude, Boolean isFake) {
        this.name = name;
        this.countryId = countryId;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isFake = isFake;
    }
}
