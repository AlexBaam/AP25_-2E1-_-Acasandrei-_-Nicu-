package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WorldCitiesPU");
        EntityManager em = emf.createEntityManager();

        // Crearea unui continent
        ContinentRepository continentRepo = new ContinentRepository(em);
        Continent africa = new Continent("Africa");
        continentRepo.create(africa);

        // Crearea unei tari
        CountryRepository countryRepo = new CountryRepository(em);
        Country nigeria = new Country("Nigeria", africa);
        countryRepo.create(nigeria);

        // Crearea unui oras
        CityRepository cityRepo = new CityRepository(em);
        City lagos = new City("Lagos", nigeria);
        cityRepo.create(lagos);

        // Verificarea entitatilor salvate
        System.out.println(continentRepo.findById(africa.getId()));
        System.out.println(countryRepo.findById(nigeria.getId()));
        System.out.println(cityRepo.findById(lagos.getId()));

        em.close();
        emf.close();
    }
}
