package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ContinentDAO continents = new ContinentDAO();
            continents.create("Europe");
            Database.getConnection().commit();
            CountryDAO countries = new CountryDAO();
            int europeId = continents.findByName("Europe");
            countries.create("Romania", "RO", europeId);
            countries.create("Ukraine", "UK", europeId);
            Database.getConnection().commit();
            countries.findByContinent(europeId);
            Database.getConnection().close();
        } catch (SQLException e) {
            System.err.println(e);
            Database.rollback();
        }
    }
}
