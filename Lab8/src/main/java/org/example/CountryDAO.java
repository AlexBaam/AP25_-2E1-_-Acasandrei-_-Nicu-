//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    public void create(String name, String code, int continentId) throws SQLException {
        Connection con = Database.getConnection();

        try {
            try (PreparedStatement pstmt = con.prepareStatement("insert into countries (name, code, continent) values (?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setString(2, code);
                pstmt.setInt(3, continentId);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findByContinent(int continentId) throws SQLException {
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement("select * from countries where continent = ?")) {
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
}
