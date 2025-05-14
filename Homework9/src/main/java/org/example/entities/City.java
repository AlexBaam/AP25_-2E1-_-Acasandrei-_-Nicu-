package org.example.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity // Aceasta clasa va fi o entitate JPA, adica o clasa care se salveaza in baza de date
@Table(name = "cities") // spunem ca entitatea corespunde cu tabelul 'cities' din baza de date, pt ca el cauta "City" :((
@NamedQuery(
        name = "City.findByName",
        query = "SELECT c FROM City c WHERE c.name LIKE :name"
)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // valoarea lui id se genereaza automat (auto increment)
    private Long id;

    private String name; // coloana pentru numele orasului

    @ManyToOne // mai multe orase pot apartine unei singure tari
    @JoinColumn(name = "country") // coloana din tabelul 'cities' care leaga orasul de tara
    private Country country;

    private Boolean capital; // daca este capitala sau nu (true/false)

    private Double latitude; // latitudinea orasului

    private Double longitude; // longitudinea orasului

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();

    // constructor gol – este obligatoriu pentru ca JPA sa poata crea obiectul
    public City(int id, String name, int country, boolean capital, double latitude, double longitude) {}

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
        this.capital = false; // valoare initiala falsa (nu este capitala)
        this.latitude = null; // nu punem valoare la inceput
        this.longitude = null; // la fel si aici
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public Boolean getCapital() {
        return capital;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + (country != null ? country.getName() : "null") +
                ", capital=" + capital +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
