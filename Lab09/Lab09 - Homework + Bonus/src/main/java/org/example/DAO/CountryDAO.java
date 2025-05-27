//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
