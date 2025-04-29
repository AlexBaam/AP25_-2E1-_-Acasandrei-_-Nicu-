//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/Cities";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Alex0307";
    private static Connection connection = null;

    private Database() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }

        return connection;
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Cities", "postgres", "Alex0307");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }

    }

    public static void rollback() {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.err.println("Error during rollback: " + e.getMessage());
            }
        }

    }
}
