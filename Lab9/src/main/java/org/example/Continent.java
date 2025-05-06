package org.example;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
