package org.example;
import javax.persistence.EntityManager;
import java.util.List;

public class CityRepository {

    // EntityManager - obiectul prin care facem operatii cu baza de date
    private final EntityManager entityManager;

    public CityRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // metoda care salveaza un oras in baza de date
    public void create(City city) {
        // incepem o tranzactie (obligatoriu pentru salvare)
        entityManager.getTransaction().begin();
        // salvam obiectul in baza de date
        entityManager.persist(city);
        // confirmam tranzactia (se face efectiv salvarea)
        entityManager.getTransaction().commit();
    }

    // metoda care cauta un oras dupa id (cheia primara)
    public City findById(long id) {
        // golosim metoda find ca sa gasim un obiect City dupa id
        return entityManager.find(City.class, id);
    }

    // metoda care returneaza toate orasele din tabela cities
    public List<City> findAll() {
        // scriem o interogare JPQL care ia toate orasele
        return entityManager.createQuery("SELECT c FROM City c", City.class)
                .getResultList(); // intoarce o lista cu toate orasele gasite
    }

    // metoda care verifica daca exista un oras cu un anumit nume
    public boolean existsByName(String name) {
        // interogare care numara cate orase au acel nume
        Long count = entityManager.createQuery(
                        "SELECT COUNT(c) FROM City c WHERE c.name = :name", Long.class)
                .setParameter("name", name) // punem parametrul pentru nume
                .getSingleResult(); // luam rezultatul
        // daca numarul este mai mare ca 0, inseamna ca orasul exista
        return count > 0;
    }
}
