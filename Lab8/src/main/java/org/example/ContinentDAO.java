
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContinentDAO {
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();

        // First check if the continent already exists
        if (findByName(name) == null) {
            try (PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO continents (name) VALUES (?)")) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();
            }
        } else {
            System.out.println("Continent '" + name + "' already exists. Skipping insert.");
        }

    }

    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();

        Integer var5;
        try (
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select id from continents where name='" + name + "'");
        ) {
            var5 = rs.next() ? rs.getInt(1) : null;
        }

        return var5;
    }
}
