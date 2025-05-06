package org.example;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractRepository<T> {

    // EntityManager pentru operatiuni cu baza de date
    protected final EntityManager entityManager;
    protected static final Logger log = Logger.getLogger(AbstractRepository.class.getName());

    public AbstractRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // metoda pt a salva orice obiect de tip T
    public void create(T entity) {
        long start = System.nanoTime();

        try{
            entityManager.getTransaction().begin(); // incepem tranzactia
            entityManager.persist(entity); // persistam obiectul in baza de date
            entityManager.getTransaction().commit(); // finalizam tranzactia

            long end = System.nanoTime();
            log.info("Execution time: " + (end - start) / 1000000 + "ms for " + entity.getClass().getSimpleName());
        } catch(Exception e){
            log.severe("Error occured during create() for " + entity.getClass().getSimpleName() + ": " + e.getMessage());
            throw e;
        }
    }

    // metoda pentru a cauta un obiect dupa ID (orice tip T)
    public T findById(Class<T> clazz, Object id) {
        long start = System.nanoTime();

        try{
            T result = entityManager.find(clazz, id);

            long end = System.nanoTime();
            log.info("Execution time: " + (end - start) / 1000000 + "ms for " + clazz.getSimpleName() + ": " + id);

            return result;
        } catch(Exception e){
            log.severe("Error occured during findById() for " + clazz.getSimpleName() + ": " + e.getMessage());
            throw e;
        }
    }

    // metoda care returneaza toate obiectele dintr-o entitate
    public List<T> findAll(Class<T> clazz) {
        return entityManager
                .createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz)
                .getResultList();
    }

    // metoda care verifica daca exista deja un obiect cu un anumit nume
    public boolean existsByName(Class<T> clazz, String name, String fieldName) {
        long start = System.nanoTime();
        try{
            Long count = entityManager.createQuery(
                            "SELECT COUNT(e) FROM " + clazz.getSimpleName() + " e WHERE e." + fieldName + " = :name", Long.class)
                    .setParameter("name", name)
                    .getSingleResult();

            long end = System.nanoTime();
            log.info("Execution time: " + (end - start) / 1000000 + "ms for " + clazz.getSimpleName() + ": " + name);

            return count > 0;
        } catch(Exception e){
            log.severe("Error occurred during existsByName() for " + clazz.getSimpleName() + ": " + e.getMessage());
            throw e;
        }
    }

    public T findByName(Class<T> clazz, String name, String fieldName) {
        long start = System.nanoTime();
        try{
            List<T> results = entityManager. createQuery(
                    "select e from " + clazz.getSimpleName() + " e WHERE e." + fieldName + " LIKE :name", clazz
            ).setParameter("name", "%" + name + "%")
                    .getResultList();

            long end = System.nanoTime();
            log.info("Execution time: " + (end - start) / 1000000 + "ms for " + clazz.getSimpleName() + ": " + name);

            return results.isEmpty() ? null : results.get(0);
        } catch(Exception e){
            log.severe("Error occurred during findByName() for " + clazz.getSimpleName() + ": " + e.getMessage());
            throw e;
        }
    }
}
