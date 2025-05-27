package org.example.lab11_springboot.client;

import org.example.lab11_springboot.model.City;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class Client implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/api/cities";

    @Override
    public void run(String... args) {
        List<City> cities = Arrays.asList(restTemplate.getForObject(baseUrl, City[].class));
        System.out.println("Cities:");
        cities.forEach(c -> System.out.println(" - " + c.getName()));

        City newCity = new City("Iasi", 1, false, 45.0, 25.0, false);
        City created = restTemplate.postForObject(baseUrl, newCity, City.class);
        System.out.println("Created city: " + created.getId() + " - " + created.getName());

        created.setName("Iasi Update");
        restTemplate.put(baseUrl + "/" + created.getId(), created);
        System.out.println("Updated Iasi.");

        restTemplate.delete(baseUrl + "/" + created.getId());
        System.out.println("Deleted Iasi.");
    }
}
