package com.example.proje.db;

public class DbTest {
    public static void main(String[] args) {
        try {
            DbConnection.getConnection();
            System.out.println("DB bağlantısı başarılı ✅");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
