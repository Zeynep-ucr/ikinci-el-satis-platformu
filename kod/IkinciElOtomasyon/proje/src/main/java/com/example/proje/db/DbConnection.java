package com.example.proje.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=IkinciElDB;" +
                    "encrypt=true;" +
                    "trustServerCertificate=true;" +
                    "user=ikinciel_user;" +
                    "password=1234;";

    static {
        try {
            // ✅ SQL Server Driver'ı zorla yükle
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL Server JDBC Driver bulunamadı!", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Veritabanına bağlanılamadı!", e);
        }
    }
}
