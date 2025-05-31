package org.example.lab11_springboot.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lab11_springboot.model.City;
import org.example.lab11_springboot.security.login.LoginRequest;
import org.example.lab11_springboot.security.login.LoginResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Client implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/api/cities";

    @Override
    public void run(String... args) throws Exception {

        // Cream un login request fiindca nu putem folosi metodele Http fiindca endpointul este securizat
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Nicu");
        loginRequest.setPassword("0307");

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(
                "http://localhost:8081/api/auth/login",
                loginRequest,
                LoginResponse.class);

        String token = loginResponse.getBody().getToken();
        System.out.println("Token: " + token);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Get ptr /api/cities
        ResponseEntity<String> getResponse = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        City[] cities = mapper.readValue(getResponse.getBody(), City[].class);

        System.out.println("Lista orașelor:");
        for (City city : cities) {
            System.out.printf(" - [%d] %s (Capitală: %b, Țara ID: %d, Lat: %.4f, Long: %.4f, Fake: %b)%n",
                    city.getId(), city.getName(), city.isCapital(),
                    city.getCountryId(), city.getLatitude(), city.getLongitude(), city.getIsFake());
        }

        City newCity = new City();
        newCity.setName("Iasi");
        newCity.setCountryId(1);
        newCity.setCapital(false);
        newCity.setLatitude(47.1585);
        newCity.setLongitude(27.6014);
        newCity.setIsFake(false);

        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        //Post ptr /api/cities
        HttpEntity<City> postEntity = new HttpEntity<>(newCity, headers);

        ResponseEntity<String> postResponse = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                postEntity,
                String.class
        );

        System.out.println("Raspuns la POST /api/cities: " + postResponse.getBody());
    }
}