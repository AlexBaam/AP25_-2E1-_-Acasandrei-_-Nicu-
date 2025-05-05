package org.example;

import java.util.*;

public class DistanceComputer {
    private List<City> allCities = new ArrayList<>();
    private double long1;
    private double long2;
    private double lat1;
    private double lat2;

    public DistanceComputer(List<City> cities) {
        allCities.addAll(cities);
    }

    public void computeAll(){
        for (int i = 0; i < allCities.size(); i++) {
            for (int j = i + 1; j < allCities.size(); j++) {
                City city1 = allCities.get(i);
                City city2 = allCities.get(j);

                    long1 = Math.toRadians(city1.getLongitude());
                    long2 = Math.toRadians(city2.getLongitude());
                    lat1 = Math.toRadians(city1.getLatitude());
                    lat2 = Math.toRadians(city2.getLatitude());

                    // Haversine formula
                    double dlon = long2 - long1;
                    double dlat = lat2 - lat1;
                    double a = Math.pow(Math.sin(dlat / 2), 2)
                            + Math.cos(lat1) * Math.cos(lat2)
                            * Math.pow(Math.sin(dlon / 2),2);

                    double c = 2 * Math.asin(Math.sqrt(a));

                    // Radius of earth in kilometers. Use 3956
                    // for miles
                    double r = 6371;

                    // calculate the result
                    double finalResult = c * r;


                System.out.printf("Distance between %s and %s: %.2f km%n",
                        city1.getName(), city2.getName(), finalResult);
            }
        }
    }
}
