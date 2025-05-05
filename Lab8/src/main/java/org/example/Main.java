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
            CityGenerator cityGenerator = new CityGenerator();
            SisterCitiesDAO sisterCities = new SisterCitiesDAO();
            SisterCityGenerator sisterCityGenerator = new SisterCityGenerator();

            continents.create(con,"Europe");
            continents.create(con,"Asia");
            System.out.println();

            int europeId = continents.findByName(con,"Europe");
            int asiaId = continents.findByName(con,"Asia");

            countries.create(con, "Romania", "RO", europeId);
            countries.create(con,"Germany", "DE", europeId);
            countries.create(con,"France", "FR", europeId);

            countries.create(con,"Japan", "JP", asiaId);

            int romaniaID = countries.findByName(con,"Romania");

            CityImporter importer = new CityImporter();
            importer.importFromCSV(con);
            System.out.println();

            System.out.println("European contries: ");
            countries.findByContinent(con, europeId);
            System.out.println();

            System.out.println("Romanian cities: ");
            cities.findByCountry(con, romaniaID);
            System.out.println();

            System.out.println("All cities: ");
            cities.findAll(con);
            System.out.println();

            List<City> realCities = cities.getAll(con);
            DistanceComputer distanceComputer = new DistanceComputer(realCities);
            System.out.println("Distance between cities: ");
            distanceComputer.computeAll();
            System.out.println();

            List<Integer> countryIds = countries.getAllCountryIds(con);
            List<City> fakeCities = cityGenerator.generator(con, 10000, countryIds);

            System.out.println("Generated cities: " + fakeCities.size());
            for (int i = 0; i < 10 && i < fakeCities.size(); i++) {
                System.out.println(fakeCities.get(i));
            }

            sisterCityGenerator.generateRandomSisters(fakeCities, 0.0001, con);

            SisterCityGraph citiesGraph = new SisterCityGraph(fakeCities, con);
            System.out.println("Graph contains " +
                    citiesGraph.getGraph().vertexSet().size() + " cities and " +
                    citiesGraph.getGraph().edgeSet().size() + " sister relationships.");

            ImageGenerator imgGen = new ImageGenerator(fakeCities, citiesGraph.getGraph(), 1200, 600);
            imgGen.drawToFile("graph.png");

            sisterCities.deleteAll(con);
            cities.deleteAll(con);
            countries.deleteAll(con);
            con.commit();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            // Optional: rollback using direct connection or a utility
        } finally {
            HikariDatabase.closeConnection();
        }
    }
}
