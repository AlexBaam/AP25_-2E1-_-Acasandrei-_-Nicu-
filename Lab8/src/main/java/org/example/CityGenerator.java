package org.example;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.util.Random;
import java.util.*;

public class CityGenerator {
    private final CityDAO cityDAO = new CityDAO();
    private final Random random = new Random();
    private final Faker faker = new Faker();

    public CityGenerator() {}

    public List<City> generator(Connection con, int number, List<Integer> countryIds){
        List<City> generatedCities = new ArrayList<>();

        for(int i = 0; i < number; i++){
            String cityName = generateName();
            int countryId = countryIds.get(random.nextInt(countryIds.size()));
            boolean isCapital = false;
            double latitude = randomLatitude();
            double longitude = randomLongitude();

            cityDAO.create(con, cityName, countryId, isCapital, latitude, longitude);

            int id = cityDAO.findByName(con, cityName, countryId);

            City city = new City(id, cityName, countryId, isCapital, latitude, longitude);
            generatedCities.add(city);
        }

        return generatedCities;
    }

    private String generateName() {
        return faker.address().cityName();
    }

    private double randomLongitude() {
        return -90 + (180 * random.nextDouble());
    }

    private double randomLatitude() {
        return -180 + (360 * random.nextDouble());
    }
}
