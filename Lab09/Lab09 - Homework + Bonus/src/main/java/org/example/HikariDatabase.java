package org.example;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HikariDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/Cities2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Alex0307";

    private static HikariDataSource hikariDataSource;

    private HikariDatabase(){
    }

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(5);

        hikariDataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException{
        return hikariDataSource.getConnection();
    }

    public static void closeConnection() throws SQLException{
        if((hikariDataSource != null) && (!hikariDataSource.isClosed())){
            hikariDataSource.close();
            System.out.println("HikariCP closed!");
        }
    }
}
