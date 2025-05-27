package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SisterCitiesDAO {
    public SisterCitiesDAO() {
    }

    public void insert(Connection con, int city1Id, int city2Id) {
        if (city1Id == city2Id) {
            return;
        }

        int min = Math.min(city1Id, city2Id);
        int max = Math.max(city1Id, city2Id);

        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO sister_cities (city1_id, city2_id) VALUES (?, ?)")) {
            pstmt.setInt(1, min);
            pstmt.setInt(2, max);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting sister city relationship: " + e.getMessage());
        }
    }

    public Map<Integer, Set<Integer>> getAllRelations(Connection con) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT city1_id, city2_id FROM sister_cities");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int a = rs.getInt("city1_id");
                int b = rs.getInt("city2_id");

                // Undirected graph: add both directions
                graph.computeIfAbsent(a, k -> new HashSet<>()).add(b);
                graph.computeIfAbsent(b, k -> new HashSet<>()).add(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to load sister relationships: " + e.getMessage());
        }

        return graph;
    }

    public void deleteAll(Connection con) {
        try (PreparedStatement stmt = con.prepareStatement("DELETE FROM sister_cities")) {
            stmt.executeUpdate();
            System.out.println("Sister city relationships deleted.");
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting sister cities: " + e.getMessage());
        }
    }


}
