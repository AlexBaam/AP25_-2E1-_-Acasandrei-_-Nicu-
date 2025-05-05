package org.example;

import java.sql.Connection;
import java.util.List;
import java.util.Random;

public class SisterCityGenerator {
    private final SisterCitiesDAO sisterDAO = new SisterCitiesDAO();
    private final Random random = new Random();

    public void generateRandomSisters(List<City> cities, double probability, Connection con) {
        int size = cities.size();

        for (int i = 0; i < size; i++) {
            int id1 = cities.get(i).getId();
            for (int j = i + 1; j < size; j++) {
                if (random.nextDouble() < probability) {
                    int id2 = cities.get(j).getId();
                    sisterDAO.insert(con, id1, id2);
                }
            }
        }

        System.out.println("Finished generating random sister connections.");
    }
}
