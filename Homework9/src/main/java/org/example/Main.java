package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Crearea unui continent
        ContinentRepository continentRepo = new ContinentRepository(em);
        Continent africa = new Continent("Africa");
        continentRepo.create(africa);
        System.out.println();

        // Crearea unei tari
        CountryRepository countryRepo = new CountryRepository(em);
        Country nigeria = new Country("Nigeria", africa);
        countryRepo.create(nigeria);
        System.out.println();

        // Crearea unui oras
        CityRepository cityRepo = new CityRepository(em);
        City lagos = new City("Lagos", nigeria);
        cityRepo.create(lagos);
        System.out.println();

        // Verificarea entitatilor salvate
        System.out.println(continentRepo.findById(africa.getId()));
        System.out.println(countryRepo.findById(nigeria.getId()));
        System.out.println(cityRepo.findById(City.class, lagos.getId()));
        System.out.println();

        System.out.println(continentRepo.findByName("Africa"));
        System.out.println(countryRepo.findByName("Nigeria"));
        System.out.println(cityRepo.findByName("Lagos"));
        System.out.println();

        em.getTransaction().begin();
        em.createQuery("DELETE FROM City").executeUpdate();
        em.createQuery("DELETE FROM Country").executeUpdate();
        em.createQuery("DELETE FROM Continent").executeUpdate();
        em.getTransaction().commit();

        em.close();
        EntityManagerFactorySingleton.closeEntityManagerFactory();
    }
}
