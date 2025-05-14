package org.example.repository;

import org.example.entities.Continent;

import javax.persistence.EntityManager;
import java.util.List;

/*
Clasa ContinentRepository ne ajuta sa lucram cu entitatile de tip Continent.
Aici sunt metode pentru salvare, cautare dupa id sau nume, si verificare daca un nume exista deja.
*/

public class ContinentRepository extends AbstractRepository<Continent> {

    public ContinentRepository(EntityManager entityManager) {
        super(entityManager);
    }

    // metoda care salveaza un obiect Continent in baza de date
    public void create(Continent continent) {
        if(!existsByName(continent.getName())) {
            entityManager.getTransaction().begin(); // incepem o tranzactie
            entityManager.persist(continent); // salvam obiectul
            entityManager.getTransaction().commit(); // confirmam tranzactia
        } else {
            System.out.println("Continent already exists");
        }
    }

    // metoda care cauta un continent dupa ID
    public Continent findById(int id) {
        return entityManager.find(Continent.class, id); // cautam cu metoda find
    }

    // metoda care cauta un continent dupa nume (exact)
    public Continent findByName(String name) {
        return super.findByName(Continent.class, name, "name");
    }

    // metoda care returneaza toate continentele din baza de date
    public List<Continent> findAll() {
        return entityManager
                .createQuery("SELECT c FROM Continent c", Continent.class) // interogare pentru toate continentele
                .getResultList(); // lista cu toate rezultatele
    }

    // metoda care verifica daca un continent exista deja cu acelasi nume
    public boolean existsByName(String name) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(c) FROM Continent c WHERE c.name = :name", Long.class)
                .setParameter("name", name) // setam parametrul pentru nume
                .getSingleResult(); // luam rezultatul

        return count > 0; // daca e mai mare ca 0, inseamna ca exista
    }

}
