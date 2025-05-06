package org.example;
import javax.persistence.EntityManager;
import java.util.List;

public class CityRepository extends AbstractRepository<City> {

    // EntityManager - obiectul prin care facem operatii cu baza de date
    //private final EntityManager entityManager;

    public CityRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public City findByName(String name) {
        return super.findByName(City.class, name, "name");
    }

}
