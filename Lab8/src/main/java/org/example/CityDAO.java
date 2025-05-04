package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CityDAO {
    public void create(Connection con, String name, int countryId, boolean capital, double lat, double lon) {
        if (findByName(con, name, countryId) != null) {
            System.out.println("City " + name + " already exists in the country! Skipping insert!");
            return;
        }

        if ((capital) && (capitalExists(con, countryId))) {
            System.out.println("Country already has a capital! Cannot insert " + name + " into the country!");
            return;
        }

        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, countryId);
            pstmt.setBoolean(3, capital);
            pstmt.setDouble(4, lat);
            pstmt.setDouble(5, lon);
            pstmt.executeUpdate();
            System.out.println("Inserted city: " + name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean capitalExists(Connection con,int countryId) {
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT 1 FROM cities WHERE country = ? AND capital = TRUE LIMIT 1")) {
            pstmt.setInt(1, countryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // returns true if a capital exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer findByName(Connection con, String name, int countryId) {
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT id FROM cities WHERE name = ? AND country = ?")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, countryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt("id") : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findByCountry(Connection con, int cityID) {
        try (PreparedStatement pstmt = con.prepareStatement(
                "select * from cities where country = ?")) {
            pstmt.setInt(1, cityID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    String countryName = rs.getString("name");
                    System.out.println(countryName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findAll(Connection con) {
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT name, country, capital, latitude, longitude FROM cities")) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("City: %s | Country ID: %d | Capital: %b | Lat: %.4f | Lon: %.4f%n",
                            rs.getString("name"),
                            rs.getInt("country"),
                            rs.getBoolean("capital"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<City> getAll(Connection con) {
        List<City> cities = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT id, name, country, capital, latitude, longitude FROM cities"
        )) {
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    City city = new City(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("country"),
                            rs.getBoolean("capital"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude")
                    );
                    cities.add(city);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }
}