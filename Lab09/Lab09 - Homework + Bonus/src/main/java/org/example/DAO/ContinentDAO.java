
package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContinentDAO {
    public void create(Connection con, String name) throws SQLException {
        // First check if the continent already exists
        if (findByName(con, name) == null) {
            try (PreparedStatement pstmt = con.prepareStatement(
                    "INSERT INTO continents (name) VALUES (?)")) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();
            }
        } else {
            System.out.println("Continent '" + name + "' already exists. Skipping insert.");
        }

    }

    public Integer findByName(Connection con, String name) throws SQLException {
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
