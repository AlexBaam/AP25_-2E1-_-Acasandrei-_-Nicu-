package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class CityImporter {

    public void importFromCSV(Connection con) {
        Path path = Paths.get("data/cities.csv"); // fisierul CSV (exel) din folderul "data"

        // il deschidem si citim datele
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            br.readLine(); // sarim peste primul rand (headerele - adica denumirea coloanelor)

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(","); //asa le-am scris in fisier, cu , intre ele

                // extragem valorile din fisier
                String name = tokens[0].trim();
                String countryCode = tokens[1].trim();
                boolean isCapital = Boolean.parseBoolean(tokens[2].trim());
                double latitude = Double.parseDouble(tokens[3].trim());
                double longitude = Double.parseDouble(tokens[4].trim());

                // gasim ID-ul tarii pe baza codului tarii
                CountryDAO countryDAO = new CountryDAO();
                Integer countryId = countryDAO.findByCode(con, countryCode);

                if (countryId != null) {
                    // daca am gasit ID-ul tarii, inseram orasul in baza de date
                    CityDAO cityDAO = new CityDAO();
                    cityDAO.create(con, name, countryId, isCapital, latitude, longitude);
                } else {
                    System.out.println("Tara cu codul " + countryCode + " nu a fost gasita!");
                }
            }

        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului CSV: " + e.getMessage());
        }
    }
}
