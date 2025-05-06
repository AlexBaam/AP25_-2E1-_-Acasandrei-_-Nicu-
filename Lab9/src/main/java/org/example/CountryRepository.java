package org.example;

import javax.persistence.EntityManager;
import java.util.List;

// clasa care se ocupa cu salvarea si cautarea tarilor in baza de date
public class CountryRepository {

    // EntityManager ne ajuta sa comunicam cu baza de date
    private final EntityManager entityManager;

    public CountryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // metoda care salveaza o tara in baza de date
    public void create(Country country) {
        entityManager.getTransaction().begin(); // incepem o tranzactie
        entityManager.persist(country); // salvam obiectul
        entityManager.getTransaction().commit(); // finalizam tranzactia
    }

    // metoda care cauta o tara dupa ID
    public Country findById(int id) {
        return entityManager.find(Country.class, id);
    }

    // metoda care cauta toate tarile cu un anumit nume
    public List<Country> findByName(String name) {
        return entityManager
                .createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class) // interogare JPQL
                .setParameter("name", name) // punem numele in interogare
                .getResultList(); // returneaza lista cu rezultate
    }

    // metoda care returneaza toate tarile din baza de date
    public List<Country> findAll() {
        return entityManager
                .createQuery("SELECT c FROM Country c", Country.class) // interogare pentru toate tarile
                .getResultList(); // returneaza lista
    }
}
