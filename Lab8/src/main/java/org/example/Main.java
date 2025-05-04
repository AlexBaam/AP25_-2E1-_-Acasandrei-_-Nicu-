package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection con = HikariDatabase.getConnection()) {
            con.setAutoCommit(false);

            ContinentDAO continents = new ContinentDAO();
            CountryDAO countries = new CountryDAO();
            CityDAO cities = new CityDAO();

            continents.create(con,"Europe");
            continents.create(con,"Asia");
            System.out.println();

            int europeId = continents.findByName(con,"Europe");
            int asiaId = continents.findByName(con,"Asia");

            countries.create(con, "Romania", "RO", europeId);
            countries.create(con,"Ukraine", "UA", europeId);
            countries.create(con,"Germany", "DE", europeId);
            countries.create(con,"France", "FR", europeId);
            countries.create(con,"Italy", "IT", europeId);

            countries.create(con,"Japan", "JP", asiaId);
            countries.create(con,"China", "CN", asiaId);

            int romaniaID = countries.findByName(con,"Romania");

            // Importa orașele din fișierul CSV
            CityImporter importer = new CityImporter();
            importer.importFromCSV(con);

            System.out.println("European contries: \n");
            countries.findByContinent(con, europeId);
            System.out.println();

            System.out.println("Asian contries: \n");
            countries.findByContinent(con, asiaId);
            System.out.println();

            System.out.println("Romanian cities: \n");
            cities.findByCountry(con, romaniaID);
            System.out.println();

            System.out.println("All cities: \n");
            cities.findAll(con);
            System.out.println();

            con.commit();

            List<City> allCities = cities.getAll(con);
            DistanceComputer distanceComputer = new DistanceComputer(allCities);

            for(City city : allCities) {
                System.out.println(city);
            }
            System.out.println();

            distanceComputer.computeAll();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            // Optional: rollback using direct connection or a utility
        } finally {
            HikariDatabase.closeConnection();
        }
    }
}
