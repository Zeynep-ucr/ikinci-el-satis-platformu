package com.example.proje.model;

import java.time.LocalDateTime;

public class GiyimUrun extends Urun {

    private String beden;

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler
    // user_id DB’den gelir
    // ==================================================
    public GiyimUrun(
            int id,
            String name,
            double fiyat,
            String beden,
            String photoPath,
            String city,
            String district,
            LocalDateTime ilanTarihi,
            int userId
    ) {
        super(
                id,
                name,
                fiyat,
                "Giyim",
                beden,          // extra_info olarak beden tutuluyor
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
        this.beden = beden;
    }

    // ==================================================
    // 🔹 UI → İlan Ver ekranı
    // userId otomatik SessionContext’ten gelir
    // ==================================================
    public GiyimUrun(
            int id,
            String name,
            double fiyat,
            String beden,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                "Giyim",
                beden,          // extra_info
                photoPath,
                city,
                district
        );
        this.beden = beden;
    }

    @Override
    public void displayDetails() {
        // JavaFX UI kullanılıyor
    }

    // ---------- GETTERS & SETTERS ----------

    public String getBeden() {
        return beden;
    }

    public void setBeden(String beden) {
        if (beden == null || beden.trim().isEmpty()) {
            throw new IllegalArgumentException("Beden boş olamaz!");
        }
        this.beden = beden;
        this.extraInfo = beden; // DB ile senkron
    }
}
