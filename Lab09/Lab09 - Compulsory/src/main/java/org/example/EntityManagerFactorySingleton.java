package org.example;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 Clasa care asigura ca avem o singura instanta de EntityManagerFactory.
 Acest lucru este important pentru ca EntityManagerFactory consuma multe resurse.
*/
public class EntityManagerFactorySingleton {

    // obiectul static care tine instanta unica
    private static EntityManagerFactory emf;

    // constructor privat – ca sa nu putem crea obiecte din aceasta clasa
    private EntityManagerFactorySingleton() {}

    // metoda care returneaza instanta unica a EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            // daca nu a fost creata inca, o cream folosind numele din persistence.xml
            emf = Persistence.createEntityManagerFactory("examplePU");
        }
        return emf;
    }
}
