package org.example;

import javax.persistence.*;

@Entity // spune ca aceasta clasa este o entitate JPA
@Table(name = "countries") // leaga clasa de tabela 'countries' din baza de date, altfel cauta "Country" :((
@NamedQuery(
        name = "Country.findByName",
        query = "SELECT c FROM Country c WHERE c.name LIKE :name"
)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // valoarea se genereaza automat (auto increment)
    private int id;

    @Column(nullable = false) // coloana name nu poate fi nula
    private String name;

    @ManyToOne // o tara apartine unui singur continent
    @JoinColumn(name = "continent_id", nullable = false) // legatura in baza de date se face prin coloana continent_id
    private Continent continent;


    // constructor gol – necesar pentru JPA
    public Country() {}

    public Country(String name, Continent continent) {
        this.name = name;
        this.continent = continent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", continent=" + continent.getName() +
                '}';
    }
}
