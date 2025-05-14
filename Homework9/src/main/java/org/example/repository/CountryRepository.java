package org.example.repository;

import org.example.entities.Country;

import javax.persistence.EntityManager;
import java.util.List;

// clasa care se ocupa cu salvarea si cautarea tarilor in baza de date
public class CountryRepository extends AbstractRepository<Country> {

    public CountryRepository(EntityManager entityManager) {
        super(entityManager);
    }

    // metoda care salveaza o tara in baza de date
    public void create(Country country) {
        if(!existsByName(country.getName())) {
            entityManager.getTransaction().begin(); // incepem o tranzactie
            entityManager.persist(country); // salvam obiectul
            entityManager.getTransaction().commit(); // finalizam tranzactia
        } else {
            System.out.println("Country already exists");
        }
    }

    // metoda care cauta o tara dupa ID
    public Country findById(int id) {
        return entityManager.find(Country.class, id);
    }

    // metoda care cauta toate tarile cu un anumit nume
    public Country findByName(String name) {
        return super.findByName(Country.class, name, "name");
    }

    // metoda care returneaza toate tarile din baza de date
    public List<Country> findAll() {
        return entityManager
                .createQuery("SELECT c FROM Country c", Country.class) // interogare pentru toate tarile
                .getResultList(); // returneaza lista
    }

    public boolean existsByName(String name) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(c) FROM Country c WHERE c.name = :name", Long.class
        ).setParameter("name", name)
                .getSingleResult();

        return count > 0;
    }
}
