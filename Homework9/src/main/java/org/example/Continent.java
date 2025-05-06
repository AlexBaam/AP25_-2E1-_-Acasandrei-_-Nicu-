package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // spune ca aceasta clasa este o entitate JPA
@NamedQuery( // Definim o interogare numita (named query)
        name = "Continent.findByName", // numele interogarii
        query = "SELECT c FROM Continent c WHERE c.name LIKE :name"
)
@Table(name = "continents") // aceasta entitate va fi legata de tabelul 'continents'
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // valoarea lui id se genereaza automat (auto increment)
    private int id;

    @Column(nullable = false, unique = true) // coloana nu poate fi nula si trebuie sa fie unica
    private String name;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Country> countries = new ArrayList<>();

    // constructor gol – este necesar pentru ca JPA sa poata crea automat obiectul
    public Continent() {}

    public Continent(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void addCountry(Country country) {
        countries.add(country);
        country.setContinent(this);
    }

    public void removeCountry(Country country) {
        countries.remove(country);
        country.setContinent(null);
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
