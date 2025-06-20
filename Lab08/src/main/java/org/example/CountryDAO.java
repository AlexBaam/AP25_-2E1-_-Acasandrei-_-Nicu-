//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CountryDAO {
    public void create(Connection con,String name, String code, int continentId) throws SQLException {
        if(findByName(con, name) == null){
                try (PreparedStatement pstmt = con.prepareStatement(
                        "insert into countries (name, code, continent) values (?, ?, ?)")) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, code);
                    pstmt.setInt(3, continentId);
                    pstmt.executeUpdate();
                }
        } else {
            System.out.println("Country " + name +" already exists.");
        }
    }


    public void findByContinent(Connection con, int continentId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "select * from countries where continent = ?")) {
            pstmt.setInt(1, continentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while(rs.next()) {
                    String countryName = rs.getString("name");
                    String countryCode = rs.getString("code");
                    System.out.println(countryName + ": " + countryCode);
                }
            }
        }

    }

    public Integer findByName(Connection con, String name) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "select id from countries where name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt("id") : null;
            }
        }
    }

    public Integer findByCode(Connection con, String code) {
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT id FROM countries WHERE code = ?")) {
            pstmt.setString(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt("id") : null; // returneaza ID-ul tarii sau null daca nu exista
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getAllCountryIds(Connection con) {
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement("SELECT id FROM countries");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve country IDs: " + e.getMessage());
        }

        return ids;
    }

    public void deleteAll(Connection con) {
        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM countries")) {
            stmt.executeUpdate();
            System.out.println("All countries deleted.");
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting countries: " + e.getMessage());
        }
    }

}
